import { ThemeColor } from '@/components/calender/CalenderConfig';
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
}
export interface IDayInfo {
  day: number;
  achieveCount: number;
  totalCount: number;
}
const Today = () => {
  return new Date().getDate();
};
const LastDay = () => {
  const now = new Date();
  const month = now.getMonth() + 1;
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
    dayInfoList.push({ day: -1, achieveCount: 0, totalCount: 0 });
  }
  for (let i = 1; i <= today; i++) {
    const existingDayInfo = dayInfo.find((info) => info.day === i);
    if (existingDayInfo) {
      dayInfoList.push(existingDayInfo);
    } else {
      dayInfoList.push({ day: i, achieveCount: 0, totalCount: 0 });
    }
  }
  for (let i = today + 1; i <= LastDay(); i++) {
    dayInfoList.push({ day: i, achieveCount: 0, totalCount: 0 });
  }
  return dayInfoList;
};

export const StreaksResponse: IStreaksReponse = {
  achieveProportion: 88,
  dayOfWeekFirst: 0,
  memberHabitList: [
    {
      memberHabitId: 1,
      alias: '팔굽혀펴기 100회',
      icon: '💪',
    },
    {
      memberHabitId: 2,
      alias: '스쿼트 100회',
      icon: '🦵',
    },
    {
      memberHabitId: 3,
      alias: '100km 달리기',
      icon: '🏃',
    },
    {
      memberHabitId: 4,
      alias: '영양제',
      icon: '🍎',
    },
    {
      memberHabitId: 5,
      alias: '기타연습',
      icon: '🎸',
    },
    {
      memberHabitId: 6,
      alias: '5시 기상',
      icon: '🔔',
    },
    {
      memberHabitId: 7,
      alias: '11시 취침',
      icon: '🛏',
    },
  ],
  dayInfo: setDayInfo(
    [
      {
        day: 1,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 2,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 4,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 5,
        achieveCount: 1,
        totalCount: 3,
      },
      {
        day: 6,
        achieveCount: 2,
        totalCount: 3,
      },
      {
        day: 7,
        achieveCount: 2,
        totalCount: 3,
      },
      {
        day: 8,
        achieveCount: 2,
        totalCount: 3,
      },
      {
        day: 9,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 11,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 12,
        achieveCount: 1,
        totalCount: 3,
      },
      {
        day: 13,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 14,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 15,
        achieveCount: 3,
        totalCount: 3,
      },
      {
        day: 16,
        achieveCount: 2,
        totalCount: 3,
      },
      {
        day: 17,
        achieveCount: 1,
        totalCount: 3,
      },
      {
        day: 18,
        achieveCount: 2,
        totalCount: 3,
      },
      {
        day: 22,
        achieveCount: 2,
        totalCount: 3,
      },
      {
        day: 23,
        achieveCount: 3,
        totalCount: 3,
      },
    ],
    0,
  ),
};

// habit별

// export const HabitStreaksResponse: IStreaksReponse = {
//   achieveProportion: 100,
//   dayOfWeekFirst: 0,
//   memberHabitList: [
//     {
//       memberHabitId: 1,
//       alias: '팔굽혀펴기 100회',
//       icon: 'path',
//     },
//     {
//       memberHabitId: 2,
//       alias: '스쿼트 100회',
//       icon: 'path',
//     },
//     {
//       memberHabitId: 3,
//       alias: '100km 달리기',
//       icon: 'book',
//     },
//   ],
//   dayInfo: setDayInfo(
//     [
//       {
//         day: 1,
//         achieveCount: 3,
//       },
//       {
//         day: 2,
//         achieveCount: 3,
//       },
//       {
//         day: 3,
//         achieveCount: 3,
//       },
//       {
//         day: 7,
//         achieveCount: 3,
//       },
//       {
//         day: 8,
//         achieveCount: 3,
//       },
//       {
//         day: 9,
//         achieveCount: 3,
//       },
//     ],
//     0,
//   ),
// };
// 2. My Theme response
interface IColorThemeResponse {
  streak_theme: ThemeColor;
}
export const ColorThemeResponse: IColorThemeResponse = {
  streak_theme: 'minchodan',
};

// 3. My Tree response
interface ITreeThemeResponse {
  tree_theme: string;
}
export const TreeThemeResponse: ITreeThemeResponse = {
  tree_theme: 'pink',
};

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
  recovery: IItem;
  gotcha_streak: IItem;
  gotcha_tree: IItem;
}

export interface IItem {
  name: string;
  introduction: string;
  description: string;
  price: number;
}

export const ItemListResponse: IItemList = {
  recovery: {
    name: '스트릭 리커버리',
    introduction: '최근 한 달 중 하나의 스트릭을 복구할 수 있어요',
    description: ' 주의! 리캡에는 포함되지 않아요',
    price: 400,
  },
  gotcha_streak: {
    name: '알쏭달쏭 스트릭',
    introduction:
      // '사용하면 프로필의 스트릭 색상을 12가지 색상과 일부 특별한 색상 중 하나로 바꿔줘요',
      '스트릭 색상을 바꿀 수 있어요',
    description:
      '아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요',
    price: 20,
  },
  gotcha_tree: {
    name: '알쏭달쏭 나무',
    introduction: '사용하면 나무의 색상을 12가지 색상 중 하나로 바꿔줘요',
    description:
      '아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, 현재 색상은 사라져요.',
    price: 20,
  },
};
