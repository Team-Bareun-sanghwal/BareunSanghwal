import { IMemberHabit } from '@/app/mock';
import { HabitBtn } from '../HabitBtn/HabitBtn';
import { NoHabits } from '@/components/main/NoHabits/NoHabits';
interface IHabitList {
  habitList: IMemberHabit[];
}

export const HabitBtnList = ({ habitList }: IHabitList) => {
  return habitList.length === 0 ? (
    <NoHabits />
  ) : (
    <div className="flex justify-center gap-4 pl-1 my-4 w-full">
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
