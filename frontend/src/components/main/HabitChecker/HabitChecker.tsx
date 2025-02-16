import { getTimeRemaining } from '@/components/calendar/util';

import Image from 'next/image';
interface IHabitCheckerProps {
  achieveCount: number;
  totalCount: number;
}
interface IHabitTrackerDto {
  name: string;
  alias: string;
  memberHabitId: number;
  habitTrackerId: number;
  icon: string;
  succeededTime: number;
  day: number;
}
interface IHabitTrackerTodayDtoList {
  habitTrackerTodayDtoList: IHabitTrackerDto[];
}
const getHabitCheckerText = (habitTrackerTodayDtoList: IHabitTrackerDto[]) => {
  let habitCheckerTitle = '오늘의 바른 생활';
  let habitCheckerText = '';
  let habitCheckerSubText = '';
  let iconPath = '';
  let isAchieved = false;
  let isHabit = true;

  const totalCount = habitTrackerTodayDtoList?.length || 0;
  const achieveCount = habitTrackerTodayDtoList.filter(
    (habit: IHabitTrackerDto) => habit.succeededTime != null,
  ).length;

  if (totalCount === 0) {
    habitCheckerText = '오늘 수행 가능한 해빗이 없어요!';
    habitCheckerSubText = '새로운 해빗을 가져보는 건 어때요?';
    iconPath = '/images/icon-check-disabled.png';
    isHabit = false;
  } else if (achieveCount < totalCount) {
    habitCheckerText = `${totalCount - achieveCount}개의 해빗이 남았어요!`;
    iconPath = '/images/icon-check-disabled.png';
  } else {
    habitCheckerText = '멋지게 해냈어요!';
    habitCheckerSubText = '오늘도 정말 멋진 하루였어요!';
    iconPath = '/images/icon-check.png';
    isAchieved = true;
  }
  return {
    habitCheckerTitle,
    habitCheckerText,
    habitCheckerSubText,
    iconPath,
    isAchieved,
    isHabit,
  };
};
export const HabitChecker = ({
  habitTrackerTodayDtoList,
}: IHabitTrackerTodayDtoList) => {
  const { hoursRemaining, minutesRemaining } = getTimeRemaining();
  const {
    habitCheckerTitle,
    habitCheckerText,
    habitCheckerSubText,
    iconPath,
    isAchieved,
    isHabit,
  } = getHabitCheckerText(habitTrackerTodayDtoList);
  return (
    <>
      {/* Left */}
      <div className="flex w-7/12 bg-custom-sky-pastel overflow-hidden rounded-xl content-center max-w-96">
        {/* Image Container */}
        <div className="-ms-6 content-center">
          <Image src={iconPath} alt="1" width={60} height={60} />
        </div>
        {/* Text Container */}
        <div className="content-center ms-2">
          <p>{habitCheckerTitle}</p>
          <p className="text-xl font-semibold">{habitCheckerText}</p>
          <span className="flex text-sm">
            {isAchieved ? (
              <>
                <p>{habitCheckerSubText}</p>
              </>
            ) : isHabit ? (
              <>
                <p>{hoursRemaining}</p>
                <p>시간&nbsp;</p>
                <p>{minutesRemaining}</p>
                <p>분&nbsp;</p>
                <p>이내에&nbsp;완료해주세요!</p>
              </>
            ) : (
              <p>{habitCheckerSubText}</p>
            )}
          </span>
        </div>
      </div>
      {/* Right */}
    </>
  );
};
