'use client';
import { StarIcon } from '@heroicons/react/24/solid';
import { useOverlay } from '@/hooks/use-overlay';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { ThemeColor } from '../CalenderConfig';
import { getToday, getDateFormat } from '../util';
import { $Fetch } from '@/apis';
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
  const Recovery = $Fetch({
    method: 'POST',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/recovery/${getDateFormat(false)}`,
    cache: 'no-cache',
  });

  const overlay = useOverlay();
  const onClickStreakRecovery = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="전체 스트릭은 복구되지만 해빗 별 스트릭은 복구되지 않아요"
        mode="RECOVERY"
        onClose={close}
        onConfirm={() => Recovoery()}
        open={isOpen}
        title={`${day}일의 스트릭을 복구하시겠어요?`}
      />
    ));
  };

  const Recovoery = () => {
    // Recovery.then((res) => {
    //   if (res.status === 200) {
    //     overlay.open(({ isOpen, close }) => (
    //       <BottomSheet
    //         description={`${day}일의 스트릭이 복구되었어요!`}
    //         mode="POSITIVE"
    //         onClose={close}
    //         open={isOpen}
    //         title={'스트릭 복구 성공!'}
    //       />
    //     ));
    //   } else {
    //     overlay.open(({ isOpen, close }) => (
    //       <BottomSheet
    //         description="스트릭 복구권은 매월 한달 무료로 제공되요! 더 필요하면 상점을 이용해주세요!"
    //         mode="NEGATIVE"
    //         onClose={close}
    //         open={isOpen}
    //         title={'스트릭 복구권이 없어요...'}
    //       />
    //     ));
    //   }
    // });

    // Sample
    const myRecovoeryCount = 0;
    if (myRecovoeryCount > 0) {
      overlay.open(({ isOpen, close }) => (
        // 구매 성공 응답 시
        <BottomSheet
          description={`${day}일의 스트릭이 복구되었어요!`}
          mode="POSITIVE"
          onClose={close}
          open={isOpen}
          title={'스트릭 복구 성공!'}
        />
      ));
    } else {
      // 실패 시
      overlay.open(({ isOpen, close }) => (
        <BottomSheet
          description="스트릭 복구권은 매월 한달 무료로 제공되요! 더 필요하면 상점을 이용해주세요!"
          mode="NEGATIVE"
          onClose={close}
          open={isOpen}
          title={'스트릭 복구권이 없어요...'}
        />
      ));
    }
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
