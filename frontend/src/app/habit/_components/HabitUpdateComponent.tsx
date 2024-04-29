'use client';

import { useState } from 'react';
import { GuideBox, HabitListBox, PlusButton, TinyButton } from '@/components';

export const HabitUpdateComponent = () => {
  const [isUpdating, setIsUpdating] = useState<boolean>(false);

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
      />

      <PlusButton />
    </div>
  );
};
