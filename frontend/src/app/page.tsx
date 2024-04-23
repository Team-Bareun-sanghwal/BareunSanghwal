import Calender from '@/components/Calender/Calender';
import { StreaksResponse, MemberStreakResponse, setDayInfo } from '@/app/mock';
import { ColorThemeResponse } from './mock';
import { NavBar } from '@/components/common/NavBar/NavBar';
import HabitChecker from '@/components/Calender/HabitChecker/HabitChecker';
import LongestStreak from '@/components/Calender/LogestStreak/LongestStreak';
import HabitBtnList from '@/components/Calender/HabitBtnList/HabitBtnList';
import HabitBtn from '@/components/Calender/HabitBtn/HabitBtn';
export default function Home() {
  const { dayOfWeekFirst, memberHabitList, dayInfo } = StreaksResponse;
  const theme = ColorThemeResponse.streak_theme;
  return (
    <>
      <>
        {/* <main className="custom-bold-text text-custom-light-green">
          hello, world!
        </main> */}
        <div className="flex overflow-x-scroll scrollbar-hide gap-4 mx-6 p-4">
          {memberHabitList.map((habit) => (
            <HabitBtn
              key={habit.memberHabitId}
              memberHabitId={habit.memberHabitId}
              alias={habit.alias}
              icon={habit.icon}
              size="L"
            />
          ))}
        </div>

        <div className="flex w-full h-36 p-4 justify-around">
          <HabitChecker
            achieveCount={dayInfo[22].achieveCount}
            totalCount={memberHabitList.length}
          />
          <LongestStreak
            longestStreakCount={MemberStreakResponse.longestStreakCount}
          />
        </div>

        <Calender
          dayOfWeekFirst={dayOfWeekFirst}
          memberHabitList={memberHabitList}
          dayInfo={dayInfo}
          themeColor={theme}
        />
      </>
      <NavBar mode="HOME" />
    </>
  );
}
