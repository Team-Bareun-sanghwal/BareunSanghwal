from error_code import MemberErrorCode

class MemberException(Exception):
    def __init__(
        self,
        error_code: MemberErrorCode
    ):
        self.error_code: MemberErrorCode = error_code
        self.message: str = error_code.message
        super().__init__(self.message)
