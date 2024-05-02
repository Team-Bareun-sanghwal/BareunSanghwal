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
  const { achieveProportion, dayInfo, dayOfWeekFirst } = streakData.data;
  const { streakName } = colorData.data;
  const { habitList } =
    habitListData.data === null ? { habitList: [] } : habitListData.data;
  console.log(streakData);
  console.log(colorData);
  console.log(habitListData);
  return (
    <>
      <Calender
        dayInfo={dayInfo}
        memberHabitList={habitList}
        dayOfWeekFirst={dayOfWeekFirst}
        themeColor={streakName}
        proportion={achieveProportion}
      />
    </>
  );
}
