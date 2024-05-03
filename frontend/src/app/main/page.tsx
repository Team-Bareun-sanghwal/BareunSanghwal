import { Calender, NavBar, HabitBtnList } from '@/components';
import { $Fetch } from '@/apis';
import { getDateFormat, getFirstDay } from '@/components/calendar/util';
import { MainTitle } from '@/components/main/MainTitle/MainTitle';
import { DailyPhrase } from '@/components/main/DailyPhrase/DailyPhrase';
import { HabitChecker } from '@/components';
import { LongestStreak } from '@/components';
export default async function Page() {
  const streakData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${getDateFormat(true)}`,
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

  //test api
  //1. 사용 가능한 스트릭 리커버리 개수
  const streakRecovoeryCount = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/recovoery-count`,
    cache: 'no-cache',
  });

  //2. 최장 스트릭 수
  const longestStreak = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/longest-streak`,
    cache: 'no-cache',
  });

  //3. 스트릭 리커버리 구매
  const purchaseStreakRecovery = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products/recovery`,
    cache: 'no-cache',
  });

  //4. 스트릭 색상 구매
  const purchaseStreakColor = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products/color/streak/${getDateFormat(true)}`,
    cache: 'no-cache',
  });

  //5. 나무 구매
  const purchaseStreakTree = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products/color/tree`,
    cache: 'no-cache',
  });

  //6. 스트릭 리커버리 사용
  const streakRecovoery = await $Fetch({
    method: 'POST',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/recovery/${getDateFormat(false)}`,
    cache: 'no-cache',
  });

  //7. 오늘 완료된 해빗들
  const habitsToday = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/non-active`,
    cache: 'no-cache',
  });

  //8. 오늘의 문구
  const dailyPhrase = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/today-phrase`,
    cache: 'no-cache',
  });

  //9. 내 보유 포인트
  const myPoint = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/point`,
    cache: 'no-cache',
  });

  const { achieveProportion, dayInfo, dayOfWeekFirst } = streakData.data;
  const { streakName } = colorData.data;
  const { habitList } =
    habitListData.data === null ? { habitList: [] } : habitListData.data;
  const longestStreakCount =
    longestStreak.data == null ? 0 : longestStreak.data.longestStreak;

  const { total, tree } = myPoint.data;
  return (
    <>
      <MainTitle
        total={habitList.length}
        succeed={habitsToday.data.memberHabitList.length}
      />
      <HabitBtnList habitList={habitList} />
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
        dayOfWeekFirst={getFirstDay() - 1}
        themeColor={streakName}
        proportion={achieveProportion}
        longestStreak={longestStreakCount}
      />
      <DailyPhrase phrase="시작은 반이 아니라 시작입니다." />
      <div className="flex h-[8rem]"></div>
      <NavBar mode="HOME" />
    </>
  );
}
