import { IMemberHabit, IDayInfo } from '@/app/mock';
import Streak from './Streak/Streak';
import DayLabel from './DayLabel/DayLabel';
import Achievement from './Acheivement/Achievement';
import MonthLabel from './MonthLabel/MonthLabel';
import HabitBtnList from './HabitBtnList/HabitBtnList';
import { ThemeColor } from './CalenderConfig';
interface ICalenderProps {
  dayOfWeekFirst: number;
  memberHabitList: IMemberHabit[];
  dayInfo: IDayInfo[];
  themeColor: ThemeColor;
}
const Calender = ({
  dayOfWeekFirst,
  memberHabitList,
  dayInfo,
  themeColor,
  ...props
}: ICalenderProps) => {
  const isUnique =
    themeColor === 'dippindots' ||
    themeColor === 'rainbow' ||
    themeColor === 'rose' ||
    themeColor === 'sunny_summer';
  return (
    <>
      <MonthLabel month={4} year={2024} />
      <HabitBtnList habitList={memberHabitList} />
      <Achievement proportion={88} themeColor={themeColor} />
      <DayLabel />
      <div className="grid grid-cols-7 gap-4 p-1 m-2.5">
        {dayInfo?.map((info, index) => (
          <Streak
            key={index}
            themeColor={themeColor}
            achieveCount={info.achieveCount}
            day={info.day}
            isUnique={isUnique}
            habitCnt={memberHabitList.length}
          />
        ))}
      </div>
    </>
  );
};
export default Calender;
