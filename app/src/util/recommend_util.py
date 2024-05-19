import pandas as pd
import numpy as np

from type.types import Job, Age, Gender
from .clustering_util import ClusterUtil

from sklearn.metrics.pairwise import cosine_similarity

from dto.response import MemberHabitRecommendationDto, MemberHabitRecommendationResDto

class RecommendUtil:
    DATA_PATH = ClusterUtil.MERGED
    CLUSTER_PATH = ClusterUtil.CLUSTERED
    COLUMNS = ["gender", "age",  "job"]


    @classmethod
    def recommend(cls, gender: Gender, birth: str, job: Job):
        gender_idx = Gender.get_index(gender=gender)
        age_idx = Age.get_index_from_birth(birth)
        job_idx = Job.get_index(job=job)
        
        # 성별, 나이, 직업 기준 코사인 유사도 계산
        input_data = pd.DataFrame([[gender_idx, age_idx, job_idx]], columns=cls.COLUMNS).values
        df_cluster = pd.read_excel(io=cls.CLUSTER_PATH)
        user_features = df_cluster[cls.COLUMNS].values
        similarities = cosine_similarity(input_data, user_features)
        

        # 각 성별, 나이, 직업 조합에 해당하는 코사인 유사도 열 추가
        df_cluster["similarity"] = similarities[0]
        
        # 추가된 코사인 유사도 열을 기준으로 내림차순 정렬
        df_sorted: pd.DataFrame = df_cluster.sort_values(by="similarity", ascending=False)
        
        # 유사도가 높은 클러스터 5개의 리스트를 얻는다.
        clusters = set()
        for cluster_idx in df_sorted["cluster"]:
            clusters.add(cluster_idx)
            if len(clusters) >= 5:
                break
        
        # 앞에서 얻은 5개의 클러스터에 해당하는 해빗 리스트를 얻는다.
        df_total = pd.read_excel(cls.DATA_PATH)
        filtered = df_total[df_total["cluster"].isin(clusters)]
        print("FILTERED")
        print(filtered.head(10))
        print("...")
        print(filtered.tail(10))

        # 그 중에서 랜덤으로 10개를 뽑아 반환한다.
        import secrets
        index_list = list(filtered.index)
        limit = len(index_list)
        habit_dto_list = []
        while len(habit_dto_list) < 10:
            i = secrets.randbelow(limit)  # 0에서 (해빗 리스트 갯수 - 1)까지
            # print(f"filtered.loc[{n}]: {filtered.loc[n]}")
            index = index_list[i]
            habit_dto_list.append(
                MemberHabitRecommendationDto(
                    habitId=int(filtered.loc[index, "habitId"]),
                    name=str(filtered.loc[index, "habit"])
                )
            )

        return MemberHabitRecommendationResDto(habitList=habit_dto_list)
