import pandas as pd
from sklearn.preprocessing import OneHotEncoder
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score
from dto.base import BaseResponse
from http import HTTPStatus

# for typing
from numpy import ndarray

from type.types import Gender, Job, Age

import os

class ClusterUtil:
    SEP = os.sep 
    DATA_DIR = f"src{SEP}data"
    SRC = f"{DATA_DIR}{SEP}no_habit.xlsx"
    DATA = f"{DATA_DIR}{SEP}with_habit.xlsx"
    CLUSTERED = f"{DATA_DIR}{SEP}clustered.xlsx"
    MERGED = f"{DATA_DIR}{SEP}merged.xlsx"

    @classmethod
    def cluster(cls) -> BaseResponse:
        print(f"ClusterUtil.cluster() is running")

        from os import path
        if not path.exists(cls.SRC):
            return BaseResponse.error(
                status=HTTPStatus.NOT_FOUND,
                message="클러스터링을 위한 데이터가 없습니다."
            )
        
        df_src = pd.read_excel(cls.SRC)
        df_src["age"] = df_src["age"].str.upper().str.strip()

        # 원-핫 인코딩을 사용하여 범주형 데이터를 수치 데이터로 변환
        # sparse_output=False -> 밀집 배열로 반환된다.
        encoder = OneHotEncoder(sparse_output=False)  
        encoded_features: ndarray = encoder.fit_transform(df_src[["gender", "age", "job"]])
        print(f"length: {len(encoded_features)}")

        # K-means 클러스터링 수행
        # 클러스터 수는 silhouette 기법으로 정하거나 임의의 수치로 정한다.
        # random_state 값은 centeroid를 설정하기 위한 random seed 값 -> seed가 같으면 clustering 결과도 같다.
        kmeans = KMeans(
            # n_clusters=cls.__get_k(
            #     encoded_features,
            #     range_from=len(encoded_features) // 2,
            #     range_to=len(encoded_features)
            # ),
            n_clusters=10,
            random_state=0
        )

        clusters: ndarray = kmeans.fit_predict(encoded_features)

        # 클러스터 결과를 no_habit DataFrame에 추가
        df_clustered = pd.read_excel(cls.SRC)
        df_clustered["cluster"] = clusters
        df_clustered["age"] = df_clustered["age"].str.upper().str.strip()
        try:
            df_clustered_to_save = pd.DataFrame(df_clustered)
            df_clustered_to_save["gender"] = df_clustered["gender"].apply(func=Gender.get_index)
            df_clustered_to_save["age"] = df_clustered["age"].apply(func=Age.get_index_from_age)
            df_clustered_to_save["job"] = df_clustered["job"].apply(func=Job.get_index)
            df_clustered_to_save.to_excel(cls.CLUSTERED, index=False)
        except PermissionError as pe:
            return BaseResponse.error(
                status=HTTPStatus.SERVICE_UNAVAILABLE,
                message="잠시 후에 다시 시도해주세요."
            )
        
        # 해빗과 클러스터를 대응시킨다.
        # df_src와 df_clustered를 "gender", "age", "job"을 기준으로 조인하여 df_merged에 "cluster" 열 추가
        df_merged = pd.read_excel(cls.DATA)
        df_merged["age"] = df_merged["age"].str.upper().str.strip()
        df_merged = pd.merge(
            df_merged,
            df_clustered[["gender", "age", "job", "cluster"]],
            on=["gender", "age", "job"],
            how="left",
        )

        df_merged["gender"] = df_merged["gender"].apply(func=Gender.get_index)
        df_merged["age"] = df_merged["age"].apply(func=Age.get_index_from_age)
        df_merged["job"] = df_merged["job"].apply(func=Job.get_index)

        try:
            df_merged.to_excel(cls.MERGED, index=False)
        except PermissionError as pe:
            return BaseResponse.error(
                status=HTTPStatus.SERVICE_UNAVAILABLE,
                message="잠시 후에 다시 시도해주세요."
            )

        return True


    def __get_k(
        encoded_features: ndarray,
        range_from=2,
        range_to=3
    ) -> int:
        print(f"ClusterUtil.__get_k() is running")
        best_score = -1
        best_k = 0

        for k in range(range_from, range_to):  # 클러스터 수를 2부터 feature 수까지 시험
            kmeans = KMeans(n_clusters=k, random_state=42)
            kmeans.fit(encoded_features)
            score = silhouette_score(encoded_features, kmeans.labels_)
            
            if score > best_score:
                best_score = score
                best_k = k

        return best_k


    # def __visualize():
    #     import matplotlib.pyplot as plt

    #     # 예시 데이터셋을 사용해 다양한 클러스터 수로 K-means 실행
    #     sse = []
    #     for k in range(1, 11):
    #         kmeans = KMeans(n_clusters=k, random_state=42)
    #         kmeans.fit(X)  # X는 데이터셋
    #         sse.append(kmeans.inertia_)

    #     # 그래프 그리기
    #     plt.plot(range(1, 11), sse, marker="o")
    #     plt.xlabel("Number of clusters")
    #     plt.ylabel("SSE")
    #     plt.show()