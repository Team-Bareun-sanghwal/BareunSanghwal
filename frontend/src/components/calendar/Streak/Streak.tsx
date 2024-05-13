'use client';
import { ShieldCheckIcon } from '@heroicons/react/24/solid';
import { StarIcon } from '@heroicons/react/24/solid';
import { useOverlay } from '@/hooks/use-overlay';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { ThemeColor } from '../CalenderConfig';
import { getToday, getMonth, getYear, convertMonthFormat } from '../util';
import { $Fetch } from '@/apis';
interface StreakProps {
  themeColor: ThemeColor;
  isUnique: boolean;
  achieveCount: number;
  achieveType?: 'NOT_EXISTED' | 'ACHEIVE' | 'NOT_ACHIEVE' | 'RECOVERY';
  dayNumber?: number;
  month?: number;
  year?: number;
  totalCount?: number;
  habitId?: number;
  onClick?: () => void;
}

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
  console.log(dayNumber, achieveCount, totalCount);
  const overlay = useOverlay();
  const onClickStreakRecovery = () => {
    if (dayNumber && !habitId && achieveType === 'NOT_ACHIEVE') {
      overlay.open(({ isOpen, close }) => (
        <BottomSheet
          description="전체 스트릭은 복구되지만 해빗 별 스트릭은 복구되지 않아요"
          mode="RECOVERY"
          onClose={close}
          onConfirm={() => Recovoery()}
          open={isOpen}
          title={`${dayNumber}일의 스트릭을 복구하시겠어요?`}
        />
      ));
    }
  };

  const Recovoery = () => {
    const MM = month ? convertMonthFormat(month) : 0;
    const DD = dayNumber ? convertMonthFormat(dayNumber) : 0;
    if (MM !== 0) {
      const response = $Fetch({
        method: 'PATCH',
        url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/recovery`,
        cache: 'no-cache',
        data: {
          date: `${year}-${MM}-${DD}`,
        },
      });
      response
        .then((res) => {
          if (res.status == 200) {
            overlay.open(({ isOpen, close }) => (
              <BottomSheet
                description={`${dayNumber}일의 스트릭이 복구되었어요!`}
                mode="POSITIVE"
                onClose={close}
                open={isOpen}
                title={'스트릭 복구 성공!'}
              />
            ));
          } else {
            overlay.open(({ isOpen, close }) => (
              <BottomSheet
                description={res.message}
                mode="NEGATIVE"
                onClose={close}
                open={isOpen}
                title={'스트릭 복구 실패!'}
              />
            ));
          }
        })
        .catch((err) => {});
    }
  };
  const streakOpacity = [10, 40, 55, 60, 70, 80, 90, 100];

  const basicStreakStyle =
    'text-white text-xl aspect-square rounded-lg relative';
  function getClassName() {
    if (achieveType === 'RECOVERY') {
      return isUnique
        ? `bg-streak-${themeColor}-7 opacity-${streakOpacity[7]} ${basicStreakStyle}`
        : `bg-streak-${themeColor} opacity-${streakOpacity[totalCount ? totalCount : 1]} ${basicStreakStyle}`;
    }
    if (habitId === 0) {
      return `bg-streak-${themeColor}-${achieveCount} opacity-${streakOpacity[7]} ${basicStreakStyle}`;
    }
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
    <button onClick={() => onClickStreakRecovery()} className={customClassName}>
      <a
        className={
          getToday(false) === dayNumber + '' &&
          getMonth(false) === month + '' &&
          getYear() === year + ''
            ? 'flex text-white text-2xl w-full h-full rounded-md border-custom-dark-gray border-2 items-center justify-center'
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
