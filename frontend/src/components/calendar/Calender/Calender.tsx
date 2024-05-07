import { IMemberHabit, IDayInfo } from '@/app/mock';
import { Streak } from '../Streak/Streak';
import { DayLabel } from '../DayLabel/DayLabel';
import { Achievement } from '../Acheivement/Achievement';
import { MonthLabel } from '../MonthLabel/MonthLabel';
import { HabitBtnList } from '../HabitBtnList/HabitBtnList';
import { ThemeColor } from '../CalenderConfig';
import { getYear, getMonth, getToday } from '@/components/calendar/util';
import { setDayInfo } from '@/app/mock';
import { HabitChecker } from '@/components/main/HabitChecker/HabitChecker';
import { LongestStreak } from '@/components/main/LongestStreak/LongestStreak';
interface ICalenderProps {
  dayOfWeekFirst: number;
  memberHabitList: IMemberHabit[];
  dayInfo: IDayInfo[];
  themeColor: ThemeColor;
  proportion: number;
  longestStreak: number;
}
export const Calender = ({
  dayOfWeekFirst,
  memberHabitList,
  dayInfo,
  themeColor,
  proportion,
  longestStreak,
  ...props
}: ICalenderProps) => {
  const isUnique =
    themeColor === 'dippindots' ||
    themeColor === 'rainbow' ||
    themeColor === 'rose' ||
    themeColor === 'sunny_summer';
  return (
    <>
      <div className="flex w-full justify-around">
        <HabitChecker
          achieveCount={
            setDayInfo(dayInfo, dayOfWeekFirst)[getToday(false) as number]
              .achieveCount
          }
          totalCount={memberHabitList.length}
        />
        <LongestStreak longestStreakCount={longestStreak} />
      </div>
      <MonthLabel month={getMonth(false)} year={getYear()} />
      <HabitBtnList habitList={memberHabitList} />
      <Achievement proportion={proportion} themeColor={themeColor} />
      <DayLabel />
      <div className="grid grid-cols-7 gap-4 p-1 m-2.5">
        {setDayInfo(dayInfo, dayOfWeekFirst).map((info, index) =>
          info.day < 0 ? (
            <div key={index}></div>
          ) : (
            <Streak
              key={index}
              themeColor={themeColor}
              achieveCount={info.achieveCount}
              day={info.day}
              isUnique={isUnique}
              habitCnt={memberHabitList.length}
            />
          ),
        )}
      </div>
    </>
  );
};
