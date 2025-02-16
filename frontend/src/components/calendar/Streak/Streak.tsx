'use client';
import { ShieldCheckIcon } from '@heroicons/react/24/solid';
import { StarIcon } from '@heroicons/react/24/solid';
import { ThemeColor } from '../CalenderConfig';
import { getToday, getMonth, getYear, convertMonthFormat } from '../util';
interface StreakProps {
  themeColor: ThemeColor;
  isUnique: boolean;
  achieveCount: number;
  achieveType?: TAchieveType;
  dayNumber?: number;
  month?: number;
  year?: number;
  totalCount?: number;
  habitId?: number;
  onClick?: () => void;
}
type TAchieveType = 'NOT_EXISTED' | 'ACHIEVE' | 'NOT_ACHIEVE' | 'RECOVERY';
export const Streak = ({
  themeColor,
  achieveCount,
  achieveType,
  dayNumber,
  month,
  year,
  isUnique,
  totalCount,
  habitId,
  ...props
}: StreakProps) => {
  const streakOpacity = [10, 40, 55, 60, 70, 80, 90, 100];
  const basicStreakStyle =
    'text-white text-xl aspect-square rounded-lg relative';
  function getClassName() {
    if (achieveType === 'RECOVERY') {
      return isUnique
        ? `bg-streak-${themeColor}-7 opacity-${streakOpacity[7]} ${basicStreakStyle}`
        : `bg-streak-${themeColor} opacity-${streakOpacity[totalCount ? totalCount : 1]} ${basicStreakStyle}`;
    }
    if(achieveType === 'NOT_EXISTED') {
      return `bg-gray-200 ${basicStreakStyle}`;
    }
    if(achieveType === 'NOT_ACHIEVE') {
      return `bg-streak-none ${basicStreakStyle}`;
    }
    if (habitId === 0) {
      return `bg-streak-${themeColor}-${achieveCount} opacity-${streakOpacity[7]} ${basicStreakStyle}`;
    }
    if (isUnique) {
      return `bg-streak-${themeColor}-${achieveCount} opacity-${streakOpacity[achieveCount]} ${basicStreakStyle}`;
    } else {
      return `bg-streak-${themeColor} opacity-${streakOpacity[achieveCount]} ${basicStreakStyle}`;
    }
  }
  const customClassName = getClassName();

  return (
    <button className={customClassName}>
      <a
        className={
          getToday(false) === dayNumber + '' &&
          getMonth(false) === month + '' &&
          getYear() === year + ''
            ? 'flex text-white text-2xl w-full h-full rounded-md border-gray-500 border-4 items-center justify-center'
            : ''
        }
      >
        {dayNumber}
      </a>

      {totalCount != 0 && totalCount === achieveCount && (
        <StarIcon className="w-4 h-4 absolute right-1 top-1 z-30" />
      )}
      {achieveType === 'RECOVERY' && totalCount && (
        <ShieldCheckIcon className="w-4 h-4 absolute right-1 top-1 z-30" />
      )}
    </button>
  );
};
