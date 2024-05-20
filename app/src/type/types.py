from enum import Enum
from datetime import date

class Job(Enum):
    STUDENT = "STUDENT"
    JOB_SEEKER ="JOB_SEEKER"
    HOUSEWIFE = "HOUSEWIFE"
    EMPLOYEE = "EMPLOYEE"
    SELF_EMPLOYED = "SELF_EMPLOYED"

    @classmethod
    def get_index(cls, job: str) -> int:
        return list(Job).index(Job(job))


class Gender(Enum):
    M = "M"
    F = "F"
    N = "N"

    @classmethod
    def get_index(cls, gender: str) -> int:
        return list(Gender).index(Gender(gender))


class Age(Enum):
    KIDS = "KIDS"
    TEEN = "TEEN"
    S20 = "20S"
    S30 = "30S"
    S40 = "40S"
    S50 = "50S"
    OLD = "OLD"

    @classmethod
    def get_index_from_age(cls, age: str):
        return list(Age).index(Age(age))

    @classmethod
    def get_index_from_birth(cls, birth: date) -> int:
        current_date = date.today()

        diff = current_date.year - birth.year
        age = cls.__get_age_from_diff(diff)

        return list(Age).index(age)
    
    @classmethod
    def __get_age_from_diff(cls, diff: int):
        if diff < 14:
            return cls.KIDS
        elif diff < 20:
            return cls.TEEN
        elif diff < 30:
            return cls.S20
        elif diff < 40:
            return cls.S30
        elif diff < 50:
            return cls.S40
        elif diff < 60:
            return cls.S50
        else:
            return cls.OLD

    