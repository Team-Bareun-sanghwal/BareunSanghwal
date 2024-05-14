import { Streak } from '../Streak/Streak';
import { DayLabel } from '../DayLabel/DayLabel';
import { Achievement } from '../Acheivement/Achievement';
import { MonthLabel } from '../MonthLabel/MonthLabel';
import { HabitBtnList } from '../HabitBtnList/HabitBtnList';
import { ThemeColor } from '../CalenderConfig';
import { IMemberHabit, IDayInfo, setDayInfo } from '@/app/mock';

interface ICalenderProps {
  dayOfWeekFirst: number;
  memberHabitList: IMemberHabit[];
  dayInfo: IDayInfo[];
  themeColor: ThemeColor;
  proportion: number;
  longestStreak: number;
  year: number;
  month: number;
  habitId?: number;
}

export const Calender = ({
  dayOfWeekFirst,
  memberHabitList,
  dayInfo,
  themeColor,
  proportion,
  longestStreak,
  year,
  month,
  habitId,
  ...props
}: ICalenderProps) => {
  const isUnique =
    themeColor === 'dippindots' ||
    themeColor === 'rainbow' ||
    themeColor === 'rose' ||
    themeColor === 'sunny_summer';
  return (
    <>
      <MonthLabel month={month + ''} year={year + ''} />
      <HabitBtnList habitId={habitId} />
      <Achievement proportion={proportion} themeColor={themeColor} />
      <DayLabel />
      <div className="grid grid-cols-7 gap-4 p-1 m-2.5">
        {setDayInfo(dayInfo, dayOfWeekFirst).map((info, index) =>
          info.dayNumber < 0 ? (
            <div key={index}></div>
          ) : (
            <Streak
              key={index}
              themeColor={themeColor}
              achieveCount={info.achieveCount}
              achieveType={info.achieveType}
              year={year}
              month={month}
              dayNumber={info.dayNumber}
              isUnique={isUnique}
              totalCount={info.totalCount}
              habitId={habitId}
            />
          ),
        )}
      </div>
    </>
  );
};
