import { IStreaksReponse, IMemberHabit, IDayInfo } from './mockserver';
import Streak from '../Streak/Streak';
import { is } from '@react-three/fiber/dist/declarations/src/core/utils';

interface ICalenderProps {
  dayOfWeekFirst: number;
  memberHabitList: IMemberHabit[];
  dayInfo: IDayInfo[];
  themeColor: string;
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
    themeColor === 'sunny-summer';
  return (
    <>
      <div>{themeColor}</div>
      <div className="grid grid-cols-7 gap-4 p-1">
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
