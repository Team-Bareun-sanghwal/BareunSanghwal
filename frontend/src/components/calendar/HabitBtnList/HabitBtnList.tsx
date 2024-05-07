import { IMemberHabit } from '@/app/mock';
import { HabitBtn } from '../HabitBtn/HabitBtn';
import { NoHabits } from '@/components/main/NoHabits/NoHabits';
import { $Fetch } from '@/apis';
interface IHabitList {
  habitId?: number;
}

export const HabitBtnList = async ({ habitId }: IHabitList) => {
  const habitListData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/habits`,
    cache: 'no-cache',
  });
  const { habitList } =
    habitListData.data === null ? { habitList: [] } : habitListData.data;

  return habitList.length === 0 ? (
    <NoHabits />
  ) : (
    <div className="flex justify-center gap-4 pl-1 my-4 w-full">
      {habitList?.map((habit: IMemberHabit) => (
        <HabitBtn
          key={habit.memberHabitId}
          memberHabitId={habit.memberHabitId}
          alias={habit.alias}
          icon={habit.icon}
          habitId={habitId}
        />
      ))}
    </div>
  );
};
