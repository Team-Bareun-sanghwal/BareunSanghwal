'use client';

import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import {
  TabBox,
  TextAreaBox,
  ImageUploadBox,
  Button,
  BottomSheet,
  AlertBox,
} from '@/components';
import { useState } from 'react';
import { useOverlay } from '@/hooks/use-overlay';
import { useRouter } from 'next/navigation';
import { writeHabit } from '../../_apis/writeHabit';

export default function Write({
  onPrev,
  onNext,
  habitTrackerId,
  authorization,
}: {
  onPrev: () => void;
  onNext: () => void;
  habitTrackerId: number;
  authorization?: string;
}) {
  const [text, setText] = useState<string | null>(null);
  const [image, setImage] = useState<File | null>(null);

  const router = useRouter();

  const overlay = useOverlay();

  const handleAlertBox = () => {
    overlay.open(({ isOpen }) => (
      <AlertBox label="해빗 기록에 실패했습니다" mode="ERROR" open={isOpen} />
    ));
    setTimeout(() => overlay.close(), 1000);
  };

  const handleWriteOverlay = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="기록을 완료한다면 오늘 기록해야 할 해빗 중 1개를 완료하고 스트릭을 유지할 수 있게 됩니다!"
        mode="POSITIVE"
        onClose={close}
        onConfirm={async () => {
          const response = await writeHabit(
            image,
            {
              habitTrackerId: habitTrackerId,
              content: text,
            },
            authorization,
          );

          close();

          if (response.status === 200) {
            router.prefetch('/main');
            onNext();
          } else handleAlertBox();
        }}
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
            onClick={() => onPrev()}
          />
          <span className="custom-bold-text">해빗 기록</span>
        </nav>

        <TabBox
          tabs={[
            {
              component: <TextAreaBox text={text} setText={setText} />,
              title: '텍스트 작성',
            },
            {
              component: <ImageUploadBox image={image} setImage={setImage} />,
              title: '이미지 첨부',
            },
          ]}
        />
      </div>

      <Button
        isActivated={text && text.length <= 100 ? true : false}
        label="완료"
        onClick={handleWriteOverlay}
      />
    </div>
  );
}
