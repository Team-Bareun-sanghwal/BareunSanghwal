'use client';

import { NavBar } from '@/components/common/NavBar/NavBar';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { useOverlay } from '@/hooks/use-overlay/useOverlay';
import { useRouter } from 'next/router';

export default function Home() {
  const overlay = useOverlay();
  const router = useRouter();
  const handleOverlay = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="해빗을 삭제하면 더 이상 기록할 수 없어요. 그래도 삭제하시겠어요? 해빗을 삭제하면 더 이상 기록할 수 없어요. 그래도 삭제하시겠어요? "
        mode="POSITIVE"
        onClose={close}
        onConfirm={close}
        open={isOpen}
        title="프로틴 주스 마시기 해빗을 삭제하시겠어요?"
      />
    ));
  };

  const handleAlertBox = () => {
    overlay.open(({ isOpen }) => (
      <AlertBox
        label="해빗 이름은 15자를 넘을 수 없어요. 해빗 이름은 15자가 될 수도 있어요."
        mode="SUCCESS"
        open={isOpen}
      />
    ));
    setTimeout(() => overlay.close(), 1000);
  };

  const data = {
    year: 2024,
    month: 1,
    memberName: 'hyunjichoigo',
    mostSucceededHabit: '자연 체험하기',
    mostSucceededMemberHabit: 'heewoong',
    averageRateByMemberHabit: 63.3,
    rateByMemberHabitList: [
      {
        name: '12시 취침',
        missCount: 1,
        actionCount: 4,
        ratio: 80,
      },
      {
        name: '5시 기상',
        missCount: 2,
        actionCount: 3,
        ratio: 60,
      },
      {
        name: '영양제 먹기',
        missCount: 2,
        actionCount: 3,
        ratio: 60,
      },
      {
        name: '점심 샐러드 먹기',
        missCount: 3,
        actionCount: 2,
        ratio: 40,
      },
      {
        name: '웨이트 1시간',
        missCount: 3,
        actionCount: 2,
        ratio: 40,
      },
    ],
    rateByHabitList: [
      {
        name: '자연 체험하기',
        ratio: 25,
      },
      {
        name: '디지털디톡스',
        ratio: 18,
      },
      {
        name: '오디오북 듣기',
        ratio: 18,
      },
      {
        name: '감사하기',
        ratio: 12,
      },
      {
        name: '기타',
        ratio: 27,
      },
    ],
    mostSubmitTime: 'EVENING',
    collectedStar: 0,
    myKeyWord: 'keyword',
    image: 'basic',
  };

  return (
    <>
      <button onClick={handleOverlay}>BottomSheet 열기</button>
      <button onClick={handleAlertBox}>AlertBox 열기</button>

      <NavBar mode="HOME" />
    </>
  );
}
