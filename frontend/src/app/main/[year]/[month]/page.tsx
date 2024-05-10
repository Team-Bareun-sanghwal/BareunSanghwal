import { Calender, NavBar, HabitBtnList } from '@/components';
import { $Fetch } from '@/apis';
import {
  getDateFormat,
  getFirstDay,
  convertMonthFormat,
} from '@/components/calendar/util';
import { MainTitle } from '@/components/main/MainTitle/MainTitle';
import { DailyPhrase } from '@/components/main/DailyPhrase/DailyPhrase';
import { HabitChecker } from '@/components';
import { LongestStreak } from '@/components';
import { MyPoint } from '@/components/point/MyPoint/MyPoint';
import Point from '@/components/point/Point/Point';
import { MyStreakRecovery } from '@/components/main/MyStreakRecovery/MyStreakRecovery';
export default async function Page(props: {
  params: { year: number; month: number };
}) {
  const year = props.params.year;
  const month = props.params.month;
  console.log(year, month);
  props.params.month;
  const streakData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${year}-${convertMonthFormat(props.params.month)}`,
    cache: 'no-cache',
  });
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

  const habitsToday = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/today`,
    cache: 'no-cache',
  });

  const { achieveProportion, dayInfo, dayOfWeekFirst } = streakData.data;

  const { habitList } =
    habitListData.data === null ? { habitList: [] } : habitListData.data;
  const longestStreakCount =
    longestStreak.data == null ? 0 : longestStreak.data.longestStreak;

  console.log();
  return (
    <div className="min-h-screen">
      <MainTitle
        habitTrackerTodayDtoList={habitsToday.data.habitTrackerTodayDtoList}
      />
      <HabitBtnList />
      <div className="flex w-full justify-around">
        <HabitChecker
          achieveCount={habitsToday.data.habitTrackerTodayDtoList.length}
          totalCount={habitList.length}
        />
        <LongestStreak />
      </div>
      <Calender
        dayInfo={dayInfo}
        memberHabitList={habitList}
        dayOfWeekFirst={dayOfWeekFirst}
        themeColor={colorData.data.streakName}
        proportion={achieveProportion}
        longestStreak={longestStreakCount}
        year={year}
        month={month}
      />
      <MyStreakRecovery />
      <DailyPhrase />
      <div className="flex h-[8rem]"></div>
      <NavBar mode="HOME" />
    </div>
  );
}
