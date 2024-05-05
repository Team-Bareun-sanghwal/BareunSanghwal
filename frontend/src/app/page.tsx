'use client';

import { NavBar } from '@/components/common/NavBar/NavBar';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { useOverlay } from '@/hooks/use-overlay/useOverlay';
import { useRouter } from 'next/navigation';
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

  return (
    <>
      <button onClick={handleOverlay}>BottomSheet 열기</button>
      <button onClick={handleAlertBox}>AlertBox 열기</button>
      <button onClick={() => router.push('/tree')}>나무로가기</button>
      <button onClick={() => router.push('/signin')}>횐가입</button>
      <NavBar mode="HOME" />
    </>
  );
}
