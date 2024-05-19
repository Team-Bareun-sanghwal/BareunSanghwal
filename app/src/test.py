import pandas as pd
from sklearn.preprocessing import OneHotEncoder
from sklearn.cluster import KMeans

# 데이터 예시
# data = {
#     'id': [1, 2, 3, 4],
#     'habit': ['걷기', '달리기', '꽃꽂이', '걷기'],
#     'gender': ['M', 'F', 'B', 'M'],
#     'age': ['KIDS', 'OLDS', 'TEEN', '20s'],
#     'job': ['STUDENT', 'EMPLOYEE', 'HOUSEWIFE', 'JOB_SEEKER']
# }


# df = pd.DataFrame()

df = pd.read_excel(io='src\\data\\data.xlsx')

# 원-핫 인코더 생성 및 학습
encoder = OneHotEncoder(sparse_output=False)  # sparse=False로 설정하면 반환되는 배열이 밀집 배열 형식
encoded_data = encoder.fit_transform(df[['habit', 'gender', 'age', 'job']])

# 인코딩 결과 확인
# encoded_df = pd.DataFrame(encoded_data, columns=encoder.get_feature_names_out(['habit', 'gender', 'age', 'job']))
# print(encoded_df.head())

# 원-핫 인코딩을 사용하여 범주형 데이터를 수치 데이터로 변환
encoder = OneHotEncoder()
encoded_features = encoder.fit_transform(df[['habit', 'gender', 'age', 'job']]).toarray()

# K-means 클러스터링 수행
# 클러스터 수는 sillouette 기법으로 정했음
# random_state 값은 centeroid를 설정하기 위한 random seed 값 -> seed가 같으면 clustering 결과도 같다.
kmeans = KMeans(n_clusters=313, random_state=0)  
clusters = kmeans.fit_predict(encoded_features)

# 클러스터 결과를 원본 DataFrame에 추가
result_df = pd.DataFrame()
result_df['Cluster']= clusters

result_df.to_excel('src\\data\\result.xlsx')


# encoded_df.to_excel('src\\data\\result.xlsx')