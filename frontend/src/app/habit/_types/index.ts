export interface ICompletedHabit {
  name: string;
  alias: string;
  memberHabitId: number;
  icon: string;
  createdAt: Date;
  succeededTime: Date;
}

// 해빗 카테고리 타입
export interface IHabitListData {
  name: string;
  habitId: number;
}

export interface IHabitListDataV2 {
  habitName: string;
  habitId: number;
}
