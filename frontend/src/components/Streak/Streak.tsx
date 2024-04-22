import {
  StarIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
} from '@heroicons/react/20/solid';
interface StreakProps {
  themeColor: string;
  isUnique: boolean;
  achieveCount: number;
  day: number;
  habitCnt: number;
  onClick?: () => void;
}

const Streak = ({
  themeColor,
  achieveCount,
  day,
  isUnique,
  habitCnt,
  ...props
}: StreakProps) => {
  const streakOpacity = [10, 40, 55, 60, 70, 80, 90, 100];
  const basicStreakStyle =
    'text-white text-xl aspect-square rounded-lg relative';
  function getClassName() {
    if (achieveCount === 0) {
      return `bg-streak-none ${basicStreakStyle}`;
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
      {day}
      {habitCnt != 0 && habitCnt == achieveCount && (
        <StarIcon className="w-4 h-4 absolute right-1 top-1" />
      )}
    </button>
  );
};
export default Streak;
