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

// 요일별 사용자 수 데이터 타입
export interface IUserAmountData {
  monday: number;
  tuesday: number;
  wednesday: number;
  thursday: number;
  friday: number;
  saturday: number;
  sunday: number;
}

export interface IRegisteredHabitData {
  habitId: number | null;
  habitName: string | null;
  isCategorySet: boolean;
  alias: string | null;
  icon: string | null;
}
