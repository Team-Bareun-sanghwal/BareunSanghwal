import { IMemberHabit } from './mockserver';
import HabitBtn from './HabitBtn';
interface IHabitList {
  habitList: IMemberHabit[];
}

const HabitBtnList = ({ habitList }: IHabitList) => {
  return (
    <div className="flex justify-center gap-4 p-1 my-4 w-full">
      {habitList?.map((habit) => (
        <HabitBtn
          key={habit.memberHabitId}
          memberHabitId={habit.memberHabitId}
          alias={habit.alias}
          icon={habit.icon}
        />
      ))}
    </div>
  );
};

export default HabitBtnList;
