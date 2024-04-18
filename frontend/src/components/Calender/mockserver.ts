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
    dayInfoList.push({ day: -1, achieveCount: 0 });
  }
  for (let i = 1; i <= today; i++) {
    const existingDayInfo = dayInfo.find((info) => info.day === i);
    if (dayInfo.find((info) => info.day === i)) {
      dayInfoList.push(existingDayInfo);
    } else {
      dayInfoList.push({ day: i, achieveCount: 0 });
    }
  }
  for (let i = today + 1; i <= LastDay(); i++) {
    dayInfoList.push({ day: i, achieveCount: 0 });
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
      icon: 'path',
    },
    {
      memberHabitId: 2,
      alias: '스쿼트 100회',
      icon: 'path',
    },
    {
      memberHabitId: 3,
      alias: '100km 달리기',
      icon: 'book',
    },
  ],
  dayInfo: setDayInfo(
    [
      {
        day: 1,
        achieveCount: 3,
      },
      {
        day: 2,
        achieveCount: 3,
      },
      {
        day: 4,
        achieveCount: 3,
      },
      {
        day: 5,
        achieveCount: 1,
      },
      {
        day: 6,
        achieveCount: 2,
      },
      {
        day: 7,
        achieveCount: 2,
      },
      {
        day: 8,
        achieveCount: 2,
      },
      {
        day: 9,
        achieveCount: 3,
      },
      {
        day: 11,
        achieveCount: 3,
      },
      {
        day: 12,
        achieveCount: 1,
      },
      {
        day: 13,
        achieveCount: 3,
      },
      {
        day: 14,
        achieveCount: 3,
      },
      {
        day: 15,
        achieveCount: 3,
      },
      {
        day: 16,
        achieveCount: 2,
      },
      {
        day: 17,
        achieveCount: 1,
      },
      {
        day: 18,
        achieveCount: 2,
      },
    ],
    0,
  ),
};
