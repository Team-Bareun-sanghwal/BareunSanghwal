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

  //2. 최장 스트릭 수
  const longestStreak = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/longest-streak`,
    cache: 'no-cache',
  });

  //7. 오늘 완료된 해빗들
  const habitsToday = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/non-active`,
    cache: 'no-cache',
  });

  console.log('look at me');
  console.log(streakData);
  const { achieveProportion, dayInfo, dayOfWeekFirst } = streakData.data;
  const { streakName } = colorData.data;
  const { habitList } =
    habitListData.data === null ? { habitList: [] } : habitListData.data;
  const longestStreakCount =
    longestStreak.data == null ? 0 : longestStreak.data.longestStreak;
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
      <DailyPhrase phrase="시작은 반이 아니라 시작입니다." />
      <div className="flex h-[8rem]"></div>
      <NavBar mode="HOME" />
    </>
  );
}
