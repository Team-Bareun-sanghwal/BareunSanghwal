import { Calender, NavBar } from '@/components';
import { $Fetch } from '@/apis';
import { convertMonthFormat } from '@/components/calendar/util';
import { DailyPhrase } from '@/components/main/DailyPhrase/DailyPhrase';
export default async function Page(props: { params: { info: number[] } }) {
  const [year, month, habitId] = props.params.info;
  console.log(year, month, habitId);
  const streakData = await $Fetch({
    method: 'GET',
    url: habitId
      ? `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(month)}/${habitId}`
      : `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(month)}`,
    cache: 'no-cache',
  });
  console.log(streakData);
  const colorData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/streak`,
    cache: 'no-cache',
  });
  const habitListData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/habits`,
    cache: 'no-cache',
  });
  const longestStreak = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/longest-streak`,
    cache: 'no-cache',
  });
  const { achieveProportion, dayInfo, dayOfWeekFirst } = streakData.data;
  const { streakName } = colorData.data;
  const { habitList } =
    habitListData.data === null ? { habitList: [] } : habitListData.data;
  const longestStreakCount =
    longestStreak.data == null ? 0 : longestStreak.data.longestStreak;
  return (
    <div className="min-h-screen">
      <Calender
        dayInfo={dayInfo}
        memberHabitList={habitList}
        dayOfWeekFirst={dayOfWeekFirst}
        themeColor={streakName}
        proportion={achieveProportion}
        longestStreak={longestStreakCount}
        year={year}
        month={month}
        habitId={habitId}
      />
      <DailyPhrase />
      <div className="flex h-[8rem]"></div>
      <NavBar mode="HOME" />
    </div>
  );
}
