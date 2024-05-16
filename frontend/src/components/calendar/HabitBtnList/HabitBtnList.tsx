import { IMemberHabit } from '@/app/mock';
import { HabitBtn } from '../HabitBtn/HabitBtn';
import { NoHabits } from '@/components/main/NoHabits/NoHabits';
import { $Fetch } from '@/apis';
import { convertMonthFormat } from '../util';
interface IHabitList {
  year: number;
  month: number;
  habitId?: number;
}

export const HabitBtnList = async ({ year, month, habitId }: IHabitList) => {
  const habitListData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/month/${year}-${convertMonthFormat(month)}`,
    cache: 'no-cache',
  });
  const { memberHabitDtoList } =
    habitListData.data === null
      ? { memberHabitDtoList: [] }
      : habitListData.data;

  return memberHabitDtoList.length === 0 ? (
    <NoHabits />
  ) : (
    <div className="flex justify-center gap-4 pl-1 my-4 w-full">
      <HabitBtn memberHabitId={-1} alias="전체" icon="All" habitId={habitId} />
      {memberHabitDtoList?.map((habit: IMemberHabit) => (
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
