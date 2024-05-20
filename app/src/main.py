from fastapi import HTTPException, Header
from dto.base import BaseResponse
from http import HTTPStatus

import logging

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import uvicorn

from util.jwt_util import JWTUtil
from util.db_util import DBUtil
from util.recommend_util import RecommendUtil

# typing
from type.entities import Member
from type.types import Gender

app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], # 특정 출처 허용
    allow_credentials=True, # 인증 정보 요구 여부
    allow_methods=["GET"],  # 특정 HTTP 메소드 허용
    allow_headers=["*"],  # 특정 HTTP 헤더 허용
    expose_headers=["*"]
)

# 로거 설정
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
# 로거 생성
logger = logging.getLogger(__name__)


@app.get("/recommendation/hello")
def health_check():
    return BaseResponse.success(
        HTTPStatus.OK,
        "おげんきです！",
        data=None
    )

@app.get("/recommendation/habits")
def recommend_habits(
    token=Header(..., alias="Authorization")
) -> BaseResponse:
    member_id: int = JWTUtil.get_member_id(token)
    print(f"mid: {member_id}")

    # 사용자 정보 얻기
    member: Member = DBUtil.find_member_by_id(member_id=member_id)
    print(f"birth: {member.birth}")
    print(f"gender: {member.gender}")
    print(f"job: {member.job}")
    print(f"id: {member.id}")

    # 추천 데이터 넣어서 응답하기
    data = RecommendUtil.recommend(member.gender, member.birth, member.job)
    print(data)

    return BaseResponse.success(
        status=HTTPStatus.OK,
        message="추천 데이터를 불러왔습니다.",
        data=data
    )



def run():
    preprocess()

    import os
    from dotenv import load_dotenv

    load_dotenv()
    uvicorn.run(
        app=app,
        host=os.getenv("SERVER_HOST"),
        port=int(os.getenv("SERVER_PORT"))
    )


def preprocess():
    # clustered data가 없으면 새로 만든다.
    from util.clustering_util import ClusterUtil
    from os import path
    if not path.exists(ClusterUtil.CLUSTERED):
        print(f'')
        print(f'ClusterUtil.cluster() is called')
        print(ClusterUtil.cluster())


if __name__ == '__main__':
    run()

