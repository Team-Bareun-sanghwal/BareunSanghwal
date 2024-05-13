import { ThemeColor } from '@/components/calendar/CalenderConfig';
// 1. Streak response
export interface IStreaksReponse {
  achieveProportion: number;
  dayOfWeekFirst: number;
  memberHabitList: IMemberHabit[];
  dayInfo: IDayInfo[];
}
export interface IMemberHabit {
  memberHabitId: number;
  alias: string;
  icon: string;
  habit?: number;
}
export interface IDayInfo {
  achieveType: 'NOT_EXISTED' | 'ACHEIVE' | 'NOT_ACHIEVE' | 'RECOVERY';
  dayNumber: number;
  achieveCount: number;
  totalCount: number;
}
const Today = () => {
  return new Date().getDate();
};
const LastDay = () => {
  const now = new Date();
  const month = now.getMonth();
  const year = now.getFullYear();
  return new Date(year, month + 1, 0).getDate();
};

export const setDayInfo = (
  dayInfo: IDayInfo[],
  dayOfWeekFirst: number,
): IDayInfo[] => {
  let dayInfoList: IDayInfo[] = [];
  const today = Today();

  for (let i = 0; i < dayOfWeekFirst; i++) {
    dayInfoList.push({
      achieveType: 'NOT_EXISTED',
      dayNumber: -1,
      achieveCount: 0,
      totalCount: 0,
    });
  }
  for (let i = 1; i <= today; i++) {
    const existingDayInfo = dayInfo.find((info) => info.dayNumber === i);
    if (existingDayInfo) {
      dayInfoList.push(existingDayInfo);
    } else {
      dayInfoList.push({
        achieveType: 'NOT_EXISTED',
        dayNumber: i,
        achieveCount: 0,
        totalCount: 0,
      });
    }
  }
  for (let i = today + 1; i <= LastDay(); i++) {
    dayInfoList.push({
      achieveType: 'NOT_EXISTED',
      dayNumber: i,
      achieveCount: 0,
      totalCount: 0,
    });
  }
  return dayInfoList;
};

export function fetchStreakTheme(): Promise<{ streak_color: ThemeColor }> {
  return new Promise((resolve) => {
    const StreakThemeResponse: { streak_color: ThemeColor } = {
      streak_color: 'minchodan',
    };
    resolve(StreakThemeResponse);
  });
}

// 3. My Tree response
interface ITreeThemeResponse {
  tree_theme: string;
}
//4. Member Streak

interface IMemberStreak {
  currentTracker: number;
  totalTracker: number;
  currentStreak: number;
  longestStreakCount: number;
}

export const MemberStreakResponse: IMemberStreak = {
  currentTracker: 3,
  totalTracker: 3,
  currentStreak: 3,
  longestStreakCount: 3,
};

//5. ItemList

export interface IItemList {
  products: IItem[];
}

export interface IItem {
  key: string;
  name: string;
  introduction: string;
  description: string;
  price: number;
}

export const ItemListResponseSample: IItemList = {
  products: [
    {
      key: 'recovery',
      name: '스트릭 리커버리',
      introduction: '최근 한 달 중 하나의 스트릭을 복구할 수 있어요',
      description: ' 주의! 리캡에는 포함되지 않아요',
      price: 400,
    },
    {
      key: 'gotcha_streak',
      name: '알쏭달쏭 스트릭',
      introduction: '스트릭 색상을 바꿀 수 있어요',
      description:
        '아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요',
      price: 20,
    },
    {
      key: 'gotcha_tree',
      name: '알쏭달쏭 나무',
      introduction: '사용하면 나무의 색상을 12가지 색상 중 하나로 바꿔줘요',
      description:
        '아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요.',
      price: 20,
    },
  ],
};
