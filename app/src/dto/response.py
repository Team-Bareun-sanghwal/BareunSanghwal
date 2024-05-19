from typing import List
from pydantic import BaseModel


class MemberHabitRecommendationDto(BaseModel):
    # def __init__(
    #     self,
    #     habit_id: int,
    #     name: str
    # ):
    #     self.habitId = habit_id
    #     self.name = name
    habitId: int
    name: str

class MemberHabitRecommendationResDto(BaseModel):
    # def __init__(
    #         self,
    #         habit_list: List[MemberHabitRecommendationDto]
    #     ):
    #         self.habitList = habit_list
    habitList: List[MemberHabitRecommendationDto]