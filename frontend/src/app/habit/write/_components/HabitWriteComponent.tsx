'use client';

import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import {
  TabBox,
  TextAreaBox,
  ImageUploadBox,
  Button,
  BottomSheet,
} from '@/components';
import { useState } from 'react';
import { useOverlay } from '@/hooks/use-overlay';

export const HabitWriteComponent = () => {
  // 진행 중인 해빗 목록 fetch
  // 완료한 해빗 목록 fetch

  const [isAlreadySet, setIsAlreadySet] = useState<boolean | null>(null);

  const overlay = useOverlay();

  const handleWriteOverlay = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="기록을 완료한다면 오늘 기록해야 할 해빗 중 3개를 완료하고 13일째 스트릭을 유지할 수 있게 됩니다!"
        mode="POSITIVE"
        onClose={close}
        onConfirm={close}
        open={isOpen}
        title="기록을 완료하시겠어요?"
      />
    ));
  };

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="flex flex-col gap-[2rem]">
        <nav className="flex self-start gap-[0.5rem] items-center">
          <ChevronLeftIcon
            className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray"
            onClick={() => {}}
          />
          <span className="custom-bold-text">해빗 기록</span>
        </nav>

        <TabBox
          tabs={[
            {
              component: <TextAreaBox />,
              title: '텍스트 작성',
            },
            {
              component: <ImageUploadBox />,
              title: '이미지 첨부',
            },
          ]}
        />
      </div>

      <Button
        isActivated={isAlreadySet === null ? false : true}
        label="완료"
        onClick={handleWriteOverlay}
      />
    </div>
  );
};
