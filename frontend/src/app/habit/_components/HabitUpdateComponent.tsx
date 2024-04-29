'use client';

import { useState } from 'react';
import {
  GuideBox,
  HabitListBox,
  PlusButton,
  TinyButton,
  BottomSheet,
  AlertBox,
} from '@/components';
import { useOverlay } from '@/hooks/use-overlay';

export const HabitUpdateComponent = () => {
  const [isUpdating, setIsUpdating] = useState<boolean>(false);

  const overlay = useOverlay();

  const handleCompleteOverlay = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="지켜 온 해빗을 이제 더 이상 수행하진 않지만 통계와 리캡 데이터는 모두 유지돼요. 해빗을 완료하시겠어요?"
        mode="POSITIVE"
        onClose={close}
        onConfirm={close}
        open={isOpen}
        title="물 2L 마시기 해빗을 완료하시겠어요?"
      />
    ));
  };

  const handleDeleteOverlay = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="해빗을 삭제하면 더 이상 기록할 수 없고 이전에 작성한 내용도 모두 지워져요. 그래도 삭제하시겠어요?"
        mode="NEGATIVE"
        onClose={close}
        onConfirm={() => {
          close();
          handleAlertBox();
        }}
        open={isOpen}
        title="물 2L 마시기 해빗을 삭제하시겠어요?"
      />
    ));
  };

  const handleAlertBox = () => {
    overlay.open(({ isOpen }) => (
      <AlertBox
        label="해빗을 성공적으로 삭제했어요."
        mode="SUCCESS"
        open={isOpen}
      />
    ));
    setTimeout(() => overlay.close(), 1000);
  };

  return (
    <div className="flex flex-col items-end gap-[1rem]">
      <GuideBox guideText="지금은 총 2개의 해빗을 관리하고 있어요. 아래 목록에서 바로 기록하러 갈 수 있습니다." />

      {!isUpdating ? (
        <TinyButton
          mode="MODIFY"
          label="해빗 편집하기"
          onClick={() => setIsUpdating(true)}
        />
      ) : (
        <TinyButton
          mode="SAVE"
          label="해빗 편집 완료"
          onClick={() => setIsUpdating(false)}
        />
      )}

      <HabitListBox
        alias="물 2L 마시기"
        createdAt={new Date('2024-04-22T00:00:00.000Z')}
        iconSrc="/images/icon-clock.png"
        mode={isUpdating ? 'UPDATE' : 'GOING'}
        name="건강하기"
        onCompleteClick={handleCompleteOverlay}
        onDeleteClick={handleDeleteOverlay}
      />

      <PlusButton />
    </div>
  );
};
