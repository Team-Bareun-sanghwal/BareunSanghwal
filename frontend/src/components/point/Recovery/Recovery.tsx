'use client';

import { Button } from '../../common/Button/Button';
import { motion } from 'framer-motion';
import { $Fetch } from '@/apis';
import { useEffect, useState } from 'react';
import { getYear, getMonth, getToday } from '@/components/calendar/util';
import { setDayInfo } from '@/app/mock';
import { RecoveryCalender } from '@/components/calendar/RecoveryCalender/RecoveryCalender';
import { IDayInfo } from '@/app/mock';
import { useOverlay } from '@/hooks/use-overlay';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';
import { PopOver } from '@/components/common/PopOver/PopOver';

interface IBottomSheetProps {
  title: string;
  description: string;
  open: boolean;
  onClose?: () => void;
  onConfirm?: () => void;
  children?: React.ReactNode;
}

const container = {
  show: { y: 0, opacity: 1 },
  hidden: { y: '100%', opacity: 0 },
};

// 스트릭 미리 확인
const getStreakInfo = async (day: number) => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/recovery/${getYear()}-${getMonth(true)}-${day}`,
    cache: 'no-cache',
  });
  return response;
};

// true : 리커버리 사용 가능
// false : 리커버리 사용 불가능
const checkMyStreak = (dayInfo: IDayInfo[]) => {
  for (const day of dayInfo) {
    if (
      day.achieveType === 'NOT_ACHIEVE' &&
      day.dayNumber != parseInt(getToday(false))
    ) {
      return true;
    }
  }
  return false;
};

// 내 리커버리 정보
const getRecoveryInfo = async () => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/recovery-count`,
    cache: 'no-cache',
  });
  return response;
};

// 리커버리 구매
const patchRecovery = async () => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products/recovery`,
    cache: 'no-cache',
  });
  return response;
};

const PurchaseRecovery = async (
  selectedDay: number,
  overlay: any,
  onClose?: () => void,
) => {
  const purchase = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/recovery`,
    cache: 'no-cache',
    data: {
      date: `${getYear()}-${getMonth(true)}-${selectedDay}`,
    },
  });
  if (purchase.status === 200) {
    overlay.open(
      ({ isOpen, close }: { isOpen: boolean; close: () => void }) => (
        <AlertBox
          label="리커버리가 성공적으로 완료되었어요!"
          mode="SUCCESS"
          open={isOpen}
        />
      ),
    );
    if (onClose) onClose();
    setTimeout(() => {
      overlay.close();
    }, 2000);
  } else {
    overlay.open(
      ({ isOpen, close }: { isOpen: boolean; close: () => void }) => (
        <AlertBox label={purchase.message} mode="ERROR" open={isOpen} />
      ),
    );
    if (onClose) onClose();
    setTimeout(() => {
      overlay.close();
    }, 2000);
  }
};

export const Recovery = ({
  title,
  description,
  open,
  onClose,
  onConfirm,
  children,
}: IBottomSheetProps) => {
  const overlay = useOverlay();
  const [days, setDays] = useState<IDayInfo[]>([]);
  const [selectedDay, setSelectedDay] = useState<number>(0);
  const descriptions = ['해빗을 달성하지 못한 날의 스트릭을 복구합니다'];
  const msg = '일의 스트릭을 복구합니다';

  const BeforeRecovery = async () => {
    try {
      const recoveryInfo = await getRecoveryInfo();
      const { total, free } = recoveryInfo.data;

      if (total === 0 && free === 0) {
        const recoveryResponse = await patchRecovery();

        if (recoveryResponse.status !== 200) {
          throw new Error(
            recoveryResponse.message || '리커버리 구매에 실패했어요!',
          );
        }
      }

      if (selectedDay > 0) {
        const streakInfo = await getStreakInfo(selectedDay);

        if (streakInfo.status === 200) {
          const { changedCurrentStreak, changedLongestStreak } =
            streakInfo.data;
          overlay.open(({ isOpen, close }) => (
            <BottomSheet
              description=""
              mode="PURCHASE_RECOVERY"
              onClose={close}
              onConfirm={() => {
                PurchaseRecovery(selectedDay, overlay, onClose);
                close();
              }}
              open={isOpen}
              title={`${selectedDay}일의 스트릭을 복구합니다`}
            >
              <div className="flex flex-col w-full ml-4 mt-4">
                <span className="flex text-2xl ">
                  현재 스트릭이{' '}
                  <p className="mx-2 text=xl font-bold">
                    {' '}
                    {changedCurrentStreak}{' '}
                  </p>
                  일로 갱신되고
                </span>
                {changedLongestStreak === -1 ? (
                  <span className="flex text-2xl ">
                    최장 스트릭은 갱신되지 않아요!.
                  </span>
                ) : (
                  <span className="flex text-2xl ">
                    최장 스트릭이{' '}
                    <p className="mx-2 text=xl font-bold">
                      {' '}
                      {changedLongestStreak}{' '}
                    </p>
                    일로 갱신돼요
                  </span>
                )}
              </div>
            </BottomSheet>
          ));
        } else {
          overlay.open(({ isOpen, close }) => (
            <AlertBox label={streakInfo.message} mode="WARNING" open={isOpen} />
          ));
          setTimeout(() => overlay.close(), 1500);
        }
      } else {
        overlay.open(({ isOpen, close }) => (
          <AlertBox
            label="리커버리를 사용할 날짜를 선택해야 해요!"
            mode="WARNING"
            open={isOpen}
          />
        ));
        setTimeout(() => overlay.close(), 1000);
      }
    } catch (error: any) {
      overlay.open(({ isOpen, close }) => (
        <AlertBox label={error.message} mode="ERROR" open={isOpen} />
      ));
      setTimeout(() => overlay.close(), 2000);
    }
  };

  useEffect(() => {
    $Fetch({
      method: 'GET',
      url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${getYear()}-${getMonth(true)}`,
      cache: 'no-cache',
    }).then((res) => {
      const { dayInfo, dayOfWeekFirst } = res.data;
      if (!checkMyStreak(dayInfo)) {
        overlay.open(({ isOpen }) => (
          <AlertBox
            label="스트릭 리커버리를 사용할 수 있는 날짜가 없어요!"
            mode="WARNING"
            open={isOpen}
          />
        ));
        setTimeout(() => overlay.close(), 1000);
      }
      setDays(setDayInfo(dayInfo, dayOfWeekFirst));
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      {open && (
        <div className="absolute top-0 z-20 left-0 w-full h-[200vh] bg-custom-black-with-opacity"></div>
      )}

      <motion.section
        variants={container}
        initial={'hidden'}
        animate={open ? 'show' : 'hidden'}
        transition={{
          type: 'spring',
          mass: 0.5,
          damping: 40,
          stiffness: 400,
        }}
        className="fixed z-30 bottom-0 left-0 w-full min-w-[32rem] min-h-[40rem] p-[1rem] rounded-t-[1rem] bg-custom-white overflow-hidden flex flex-col"
      >
        <div className="grow relative items-center">
          <div className="w-full pl-[1rem] py-[1rem] flex flex-col gap-[1rem] content-center">
            <span className="flex items-center custom-semibold-text text-pretty">
              <PopOver
                title="스트릭 리커버리?"
                description={[
                  '해빗을 달성하지 못한 날의 스트릭을 복구 할 수 있습니다!',
                  '한달에 한 번 무료 스트릭 리커버리가 지급되며, 이후에는 포인트로 구매해야 합니다.',
                  '구매 할 때마다 이전 가격의 두 배로 갱신되고, 이는 다음 달에 초기화 됩니다',
                ]}
              />
              {title}
            </span>
            <span className="text-xl text-pretty">{descriptions[0]}</span>
            <div className="flex w-full justify-center">
              {days ? (
                days.length ? (
                  <RecoveryCalender
                    days={days}
                    selected={selectedDay}
                    setSelected={setSelectedDay}
                  />
                ) : (
                  <div>데이터가 없습니다.</div>
                )
              ) : (
                <div>로딩중...</div>
              )}
            </div>
            {selectedDay > 0 && (
              <span className="w-full text-center text-2xl text-pretty">
                {selectedDay}
                {msg}
              </span>
            )}
          </div>
        </div>

        <div className="flex gap-[1rem] mt-[1rem]">
          {onClose && (
            <Button isActivated={false} label="취소" onClick={onClose} />
          )}
          {onConfirm && (
            <Button
              isActivated={selectedDay > 0}
              label="확인"
              onClick={BeforeRecovery}
            />
          )}
        </div>
      </motion.section>
    </>
  );
};
