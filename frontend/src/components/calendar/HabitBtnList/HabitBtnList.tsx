import { IMemberHabit } from '@/app/mock';
import { HabitBtn } from '../HabitBtn/HabitBtn';
interface IHabitList {
  habitList: IMemberHabit[];
}

export const HabitBtnList = ({ habitList }: IHabitList) => {
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
