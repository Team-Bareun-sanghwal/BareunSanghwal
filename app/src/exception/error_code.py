from http import HTTPStatus
from typing import Tuple

class MemberErrorCode:
    NOT_FOUND_MEMBER: Tuple = (HTTPStatus.NOT_FOUND, "존재하지 않는 사용자입니다")
