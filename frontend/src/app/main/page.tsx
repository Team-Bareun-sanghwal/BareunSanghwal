import { getThisMonth, getThisYear } from '@/components/calendar/util';
import { Achievement } from '@/components';
import { MonthLabel } from '@/components';
import { DayLabel } from '@/components';
import { Calender } from '@/components';
import { $Fetch } from '@/apis';
export default async function Page() {
  const streakData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_API_URL}/streaks/2024-04`,
    cache: 'no-cache',
  });
  const colorData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_API_URL}/members/color/streak`,
    cache: 'no-cache',
  });
  const habitListData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_API_URL}/members/habits`,
    cache: 'no-cache',
  });
  const { acheiveProportion, dayInfo, dayOfWeekFirst } = streakData.data;
  const { streakName } = colorData.data;
  const { habitList } = habitListData.data;
  console.log(streakData);
  console.log(colorData);
  console.log(habitListData);
  return (
    <>
      <Achievement proportion={acheiveProportion} themeColor={streakName} />
      <MonthLabel year={getThisYear()} month={getThisMonth()} />
      <DayLabel />
      <Calender
        dayInfo={dayInfo}
        memberHabitList={habitList}
        dayOfWeekFirst={dayOfWeekFirst}
        themeColor={streakName}
      />
    </>
  );
}
