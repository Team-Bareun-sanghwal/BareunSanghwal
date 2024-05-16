import { Calender } from '@/components';
import { $Fetch } from '@/apis';
import { ThemeColor } from '@/components/calendar/CalenderConfig';
import { convertMonthFormat } from '@/components/calendar/util';
export default async function Page(props: { params: { info: number[] } }) {
  const [year, month, habitId] = props.params.info;
  const streakData = await $Fetch({
    method: 'GET',
    url: habitId
      ? `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(month)}/${habitId}`
      : `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(month)}`,
    cache: 'no-cache',
  });

  const habitListData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/month/${year}-${convertMonthFormat(month)}`,
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
    </div>
  );
}