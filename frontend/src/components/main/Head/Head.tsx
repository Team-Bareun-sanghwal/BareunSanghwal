import { MainTitle } from '../MainTitle/MainTitle';
import { HabitBtnList, HabitChecker, LongestStreak } from '@/components';
import { $Fetch } from '@/apis';
import { HabitShortcut } from '../HabitShorcut/HabitShortcut';
import Notify from '@/components/common/Notify/Notify';

export const Head = async () => {
  const habitsToday = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/today`,
    cache: 'no-cache',
  });
  const habitList = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/habits`,
    cache: 'no-cache',
  });
  const habitsTodayData = habitsToday.data;
  const habitListData =
    habitList.data === null ? { habitList: [] } : habitList.data;
  return (
    <div>
      <MainTitle
        habitTrackerTodayDtoList={habitsTodayData.habitTrackerTodayDtoList}
      />
      <Notify/>
      <HabitShortcut
        allHabits={habitListData.habitList}
        todayHabits={habitsTodayData.habitTrackerTodayDtoList}
      />

      <div className="flex w-full justify-around">
        <HabitChecker
          habitTrackerTodayDtoList={habitsTodayData.habitTrackerTodayDtoList}
        />
        <LongestStreak />
      </div>
    </div>
  );
};
