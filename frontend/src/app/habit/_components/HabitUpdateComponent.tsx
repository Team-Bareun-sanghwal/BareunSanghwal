'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import {
  GuideBox,
  HabitListBox,
  PlusButton,
  TinyButton,
  BottomSheet,
  AlertBox,
} from '@/components';
import { useOverlay } from '@/hooks/use-overlay';
import { IActivatedHabit } from '../_types';
import { deleteActivatedHabit } from '../_apis/deleteActivatedHabit';

export const HabitUpdateComponent = ({
  activatedHabitListData,
}: {
  activatedHabitListData: IActivatedHabit[];
}) => {
  const [isUpdating, setIsUpdating] = useState<boolean>(false);

  const router = useRouter();
  const overlay = useOverlay();

  const handleCompleteOverlay = (memberHabitId: number, alias: string) => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="지켜 온 해빗을 이제 더 이상 수행하진 않지만 통계와 리캡 데이터는 모두 유지돼요. 해빗을 완료하시겠어요?"
        mode="POSITIVE"
        onClose={close}
        onConfirm={async () => {
          await deleteActivatedHabit(memberHabitId, false);
          close();
          handleAlertBox('해빗을 성공적으로 완료했어요.', 'SUCCESS');
        }}
        open={isOpen}
        title={`${alias} 해빗을 완료하시겠어요?`}
      />
    ));
  };

  const handleDeleteOverlay = (memberHabitId: number, alias: string) => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="해빗을 삭제하면 더 이상 기록할 수 없고 이전에 작성한 내용도 모두 지워져요. 그래도 삭제하시겠어요?"
        mode="NEGATIVE"
        onClose={close}
        onConfirm={async () => {
          await deleteActivatedHabit(memberHabitId, true);
          close();
          handleAlertBox('해빗을 성공적으로 삭제했어요.', 'SUCCESS');
        }}
        open={isOpen}
        title={`${alias} 해빗을 삭제하시겠어요?`}
      />
    ));
  };

  const handleAlertBox = (
    message: string,
    mode: 'SUCCESS' | 'WARNING' | 'ERROR',
  ) => {
    overlay.open(({ isOpen }) => (
      <AlertBox label={message} mode={mode} open={isOpen} />
    ));
    setTimeout(() => overlay.close(), 1000);
  };

  return (
    <div className="flex flex-col items-end gap-[1rem]">
      <GuideBox
        guideText={`지금은 총 ${activatedHabitListData.length}개의 해빗을 관리하고 있어요. 아래 목록에서 바로 기록하러 갈 수 있습니다.`}
      />

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

      {activatedHabitListData.map((habit) => {
        return (
          <HabitListBox
            key={`habit-${habit.memberHabitId}`}
            alias={habit.alias}
            createdAt={new Date(habit.createdAt)}
            iconSrc={habit.icon}
            mode={isUpdating ? 'UPDATE' : 'GOING'}
            name={habit.name}
            currentStreak={habit.currentStreak}
            memberHabitId={habit.memberHabitId}
            onCompleteClick={() =>
              handleCompleteOverlay(habit.memberHabitId, habit.alias)
            }
            onDeleteClick={() =>
              handleDeleteOverlay(habit.memberHabitId, habit.alias)
            }
            onRegisterClick={() => {
              if (habit.isSucceeded) {
                handleAlertBox('이미 오늘 기록을 마쳤습니다', 'WARNING');
              } else if (habit.habitTrackerId === 0) {
                handleAlertBox(
                  '오늘은 해빗을 기록하는 날이 아닙니다',
                  'WARNING',
                );
              } else {
                router.push(`/habit/write/${habit.habitTrackerId}`);
              }
            }}
          />
        );
      })}

      <PlusButton
        onClick={() => {
          if (activatedHabitListData.length >= 7) {
            handleAlertBox('해빗은 7개까지만 관리할 수 있어요.', 'ERROR');
          } else {
            router.push('/habit/register');
          }
        }}
      />
    </div>
  );
};
