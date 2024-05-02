import { Calender } from '@/components';
import { $Fetch } from '@/apis';
import { getToday } from '@/components/calendar/util';
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

  //test api
  //1. 사용 가능한 스트릭 리커버리 개수
  const streakRecovoeryCount = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_API_URL}/members/recovoery-count`,
    cache: 'no-cache',
  });

  //2. 최장 스트릭 수
  const longestStreak = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_API_URL}/members/longest-streak`,
    cache: 'no-cache',
  });

  //3. 스트릭 리커버리 구매
  const purchaseStreakRecovery = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_API_URL}/products/recovery`,
    cache: 'no-cache',
  });

  //4. 스트릭 색상 구매
  const purchaseStreakColor = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_API_URL}/products/color/streak/2024-05-02`,
    cache: 'no-cache',
  });

  //5. 나무 구매
  const purchaseStreakTree = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_API_URL}/products/color/tree`,
    cache: 'no-cache',
  });

  //6. 스트릭 리커버리 사용
  const streakRecovoery = await $Fetch({
    method: 'POST',
    url: `${process.env.NEXT_PUBLIC_API_URL}/streaks/recovery/${}`,
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
