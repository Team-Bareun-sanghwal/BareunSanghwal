import { IMemberHabit } from '@/app/mock';
import HabitBtn from '../HabitBtn/HabitBtn';
import { BtnSize } from '../util';
interface IHabitListProps {
  habitList: IMemberHabit[];
  size?: BtnSize;
}

const HabitBtnList = ({ habitList, size }: IHabitListProps) => {
  return (
    <>
      <div className="flex justify-center gap-4 p-1 my-4 w-full ">
        {habitList?.map((habit) => (
          <HabitBtn
            key={habit.memberHabitId}
            memberHabitId={habit.memberHabitId}
            alias={habit.alias}
            icon={habit.icon}
            size={size}
          />
        ))}
      </div>
    </>
  );
};

export default HabitBtnList;
