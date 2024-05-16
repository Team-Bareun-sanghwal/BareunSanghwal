import { IMemberHabit } from '@/app/mock';
import { CalenderHabitButton } from '../CalenderHabitButton/CalenderHabitButton';
import { NoHabits } from '@/components/main/NoHabits/NoHabits';

interface IHabitList {
  habitId: number;
  setHabitId: React.Dispatch<React.SetStateAction<number>>;
  memberHabitDtoList: IMemberHabit[];
}

export const HabitBtnList = ({
  habitId,
  setHabitId,
  memberHabitDtoList,
}: IHabitList) => {
  return memberHabitDtoList.length === 0 ? (
    <NoHabits />
  ) : (
    <div className="flex justify-center gap-4 pl-1 my-4 w-full">
      {memberHabitDtoList?.map((habit: IMemberHabit) => (
        <CalenderHabitButton
          key={habit.memberHabitId}
          memberHabitId={habit.memberHabitId}
          alias={habit.alias}
          icon={habit.icon}
          habitId={habitId}
          setHabitId={setHabitId}
        />
      ))}
    </div>
  );
};
