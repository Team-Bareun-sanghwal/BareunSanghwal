'use client';
import { StarIcon } from '@heroicons/react/24/solid';
import { ThemeColor } from '../CalenderConfig';
import { useOverlay } from '@/hooks/use-overlay';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { getToday } from '../util';
interface StreakProps {
  themeColor: ThemeColor;
  isUnique: boolean;
  achieveCount: number;
  day?: number;
  habitCnt?: number;
  onClick?: () => void;
}

export const Streak = ({
  themeColor,
  achieveCount,
  day,
  isUnique,
  habitCnt,
  ...props
}: StreakProps) => {
  const overlay = useOverlay();
  const onClickStreakRecovery = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="전체 스트릭은 복구되지만 해빗 별 스트릭은 복구되지 않아요"
        mode="RECOVERY"
        onClose={close}
        onConfirm={close}
        open={isOpen}
        title={`${day}일의 스트릭을 복구하시겠어요?`}
      />
    ));
  };
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
  const onClick = () => {
    console.log(day);
  };
  return (
    <button onClick={() => onClickStreakRecovery()} className={customClassName}>
      <a
        className={getToday(false) === day ? 'text-2xl border-b-2 w-8 h-8' : ''}
      >
        {day}
      </a>
      {habitCnt != 0 && habitCnt == achieveCount && (
        <StarIcon className="w-4 h-4 absolute right-1 top-1" />
      )}
    </button>
  );
};
