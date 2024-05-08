import { Calender, NavBar, HabitBtnList } from '@/components';
import { $Fetch } from '@/apis';
import { convertMonthFormat } from '@/components/calendar/util';
import { MainTitle } from '@/components/main/MainTitle/MainTitle';
import { DailyPhrase } from '@/components/main/DailyPhrase/DailyPhrase';
import { HabitChecker } from '@/components';
import { LongestStreak } from '@/components';
export default async function Page(props: {
  params: { year: number; month: number; habitId: number };
}) {
  const { year, month, habitId } = props.params;
  props.params.month;
  const streakData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(props.params.month)}/${habitId}`,
    cache: 'no-cache',
  });
  const colorData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/color/streak`,
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

  const habitsToday = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/non-active`,
    cache: 'no-cache',
  });

  const dailyPhrase = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/today-phrase`,
    cache: 'no-cache',
  });
  const { achieveProportion, dayInfo, dayOfWeekFirst } = streakData.data;
  const { streakName } = colorData.data;
  const { habitList } =
    habitListData.data === null ? { habitList: [] } : habitListData.data;
  const longestStreakCount =
    longestStreak.data == null ? 0 : longestStreak.data.longestStreak;
  console.log(dailyPhrase.data);
  return (
    <>
      <MainTitle
        total={habitList.length}
        succeed={habitsToday.data.memberHabitList.length}
      />
      <HabitBtnList habitId={habitId} />
      <div className="flex w-full justify-around">
        <HabitChecker
          achieveCount={habitsToday.data.memberHabitList.length}
          totalCount={habitList.length}
        />
        <LongestStreak longestStreakCount={longestStreakCount} />
      </div>
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
    </>
  );
}
