from typing import Optional, Any
from pydantic import BaseModel

class BaseResponse(BaseModel):
    status: int
    message: str
    data: Optional[Any] = None

    def __str__(self) -> str:
        return f'{{\n    status: {self.status},\n    message: {self.message},\n    data: {self.data}\n}}'

    @classmethod
    def success(
        cls,
        status: int,
        message: str,
        data: Optional[Any] = None
    ):
        return cls(status=status, message=message, data=data)

    @classmethod
    def error(
        cls,
        status: int,
        message: str
    ):
        return cls(status=status, message=message)