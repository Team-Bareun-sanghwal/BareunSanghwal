import { MainTitle } from '../MainTitle/MainTitle';
import { HabitChecker, LongestStreak } from '@/components';
import { $Fetch } from '@/apis';
import { HabitShortcut } from '../HabitShorcut/HabitShortcut';
import Notify from '@/components/common/Notify/Notify';

export const Head = async () => {
  const habitsToday = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/today`,
    cache: 'default',
  });
  const habitList = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/active`,
    cache: 'default',
  });
  const habitsTodayData = habitsToday.data;
  const habitListData =
    habitList.data.memeberHabitDtoList === null
      ? { habitList: [] }
      : habitList.data.memeberHabitDtoList;
  return (
    <div>
      <MainTitle
        habitTrackerTodayDtoList={habitsTodayData.habitTrackerTodayDtoList}
      />
      <Notify />
      <HabitShortcut
        allHabits={habitListData}
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
