'use client';

import { NavBar } from '@/components/common/NavBar/NavBar';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { useOverlay } from '@/hooks/use-overlay/useOverlay';

export default function Home() {
  const overlay = useOverlay();

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

  return (
    <>
      <button onClick={handleOverlay}>모달 창 열기</button>

      <NavBar mode="HOME" />
    </>
  );
}
