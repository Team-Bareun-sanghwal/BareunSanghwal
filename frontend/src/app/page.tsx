import Calender from '@/components/calender/Calender';
import { StreaksResponse, MemberStreakResponse, setDayInfo } from '@/app/mock';
import { ColorThemeResponse } from './mock';
import { NavBar } from '@/components/common/NavBar/NavBar';
import HabitChecker from '@/components/calender/HabitChecker/HabitChecker';
import LongestStreak from '@/components/main/LongestStreak/LongestStreak';
import HabitBtnList from '@/components/calender/HabitBtnList/HabitBtnList';
import HabitBtn from '@/components/calender/HabitBtn/HabitBtn';
import { BellIcon } from '@heroicons/react/24/outline';
import Image from 'next/image';
export default function Home() {
  const { dayOfWeekFirst, memberHabitList, dayInfo } = StreaksResponse;
  const theme = ColorThemeResponse.streak_theme;
  const getStar =
    dayInfo[22].achieveCount === dayInfo[22].totalCount &&
    dayInfo[22].totalCount !== 0;
  return (
    <>
      <div className="flex p-4 px-8 text-3xl justify-between">
        <div className="flex">
          <div className="w-8 h-8 content-center">
            {getStar ? (
              <Image
                src="/images/icon-star.png"
                alt="1"
                width={12}
                height={12}
              />
            ) : (
              <Image
                src="/images/icon-star-disabled.png"
                alt="1"
                width={12}
                height={12}
              />
            )}
          </div>
          <p>오늘의 해빗</p>
          <p className="ml-2 flex text-lg items-end">
            {dayInfo[22].achieveCount}개 남았어요!
          </p>
        </div>
        <button>
          <BellIcon className="w-8" />
        </button>
      </div>
      <div className="flex overflow-x-scroll scrollbar-hide gap-2 mx-6 px-4 py-2 ">
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
      <NavBar mode="HOME" />
    </>
  );
}
