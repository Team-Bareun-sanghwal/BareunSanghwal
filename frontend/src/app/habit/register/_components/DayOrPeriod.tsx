'use client';

import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import {
  Button,
  ProgressBox,
  TabBox,
  HabitDayChart,
  HabitRegisterDayChart,
  HabitPeriodSelectBox,
  GuideBox,
  HabitRegisterBottomSheet,
  HabitListBox,
} from '@/components';
import { useState } from 'react';
import { useOverlay } from '@/hooks/use-overlay';
import {
  IUserAmountData,
  IRegisteredHabitData,
  ISimpleHabitListData,
} from '../../_types';
import { registerHabit } from '../../_apis/registerHabit';

interface IDayOrPeriodStepComponent {
  onPrev: () => void;
  onNext: () => void;
  userAmountData: IUserAmountData;
  simpleHabitListData: ISimpleHabitListData[];
  data: IRegisteredHabitData;
}

export default function DayOrPeriod({
  onPrev,
  onNext,
  userAmountData,
  simpleHabitListData,
  data,
}: IDayOrPeriodStepComponent) {
  const [dayOfWeek, setDayOfWeek] = useState<number[]>([]);
  const [period, setPeriod] = useState<number | null>(null);

  const overlay = useOverlay();

  const handleRegisterOverlay = () => {
    overlay.open(({ isOpen, close }) =>
      !period ? (
        <HabitRegisterBottomSheet
          element={
            <HabitListBox
              alias={data?.alias}
              completedAt={new Date()}
              createdAt={new Date()}
              dayList={dayOfWeek
                .sort((a, b) => a - b)
                .map((num) => {
                  return num === 1
                    ? '월'
                    : num === 2
                      ? '화'
                      : num === 3
                        ? '수'
                        : num === 4
                          ? '목'
                          : num === 5
                            ? '금'
                            : num === 6
                              ? '토'
                              : '일';
                })}
              iconSrc={data?.icon || ''}
              mode="REGISTER"
              name={data?.habitName}
            />
          }
          onClose={close}
          onConfirm={() => {
            close();
            onNext();

            if (data.habitId && data.alias && data.icon)
              registerHabit(
                data.habitId,
                data.alias,
                data.icon,
                dayOfWeek.length === 0 ? null : dayOfWeek,
                period,
              );
          }}
          open={isOpen}
        />
      ) : (
        <HabitRegisterBottomSheet
          element={
            <HabitListBox
              alias={data?.alias}
              completedAt={new Date()}
              createdAt={new Date()}
              period={period}
              iconSrc={data?.icon || ''}
              mode="REGISTER"
              name={data?.habitName}
            />
          }
          onClose={close}
          onConfirm={() => {
            close();
            onNext();

            if (data.habitId && data.alias && data.icon)
              registerHabit(
                data.habitId,
                data.alias,
                data.icon,
                dayOfWeek.length === 0 ? null : dayOfWeek,
                period,
              );
          }}
          open={isOpen}
        />
      ),
    );
  };

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[2rem] pb-[2rem]">
        <nav className="flex self-start gap-[0.5rem] items-center mb-[1rem]">
          <ChevronLeftIcon
            className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray"
            onClick={onPrev}
          />
          <span className="custom-bold-text">신규 해빗 등록</span>
        </nav>

        <ProgressBox
          stages={['추천', '카테고리/별칭 설정', '요일 설정', '완료']}
          beforeStageIndex={2}
        ></ProgressBox>

        <GuideBox guideText="정확한 요일에 기록하고 싶다면 요일 탭에서, 특정 주기마다 기록하고 싶다면 주기 탭에서 설정해주세요!" />

        <TabBox
          tabs={[
            {
              title: '요일',
              component: (
                <div className="flex flex-col gap-[3rem]">
                  <HabitRegisterDayChart
                    dayOfWeek={dayOfWeek}
                    setDayOfWeek={setDayOfWeek}
                    setPeriod={setPeriod}
                    habitRegisterDayList={[
                      {
                        englishDayName: 'monday',
                        registerCount: userAmountData.monday,
                      },
                      {
                        englishDayName: 'tuesday',
                        registerCount: userAmountData.tuesday,
                      },
                      {
                        englishDayName: 'wednesday',
                        registerCount: userAmountData.wednesday,
                      },
                      {
                        englishDayName: 'thursday',
                        registerCount: userAmountData.thursday,
                      },
                      {
                        englishDayName: 'friday',
                        registerCount: userAmountData.friday,
                      },
                      {
                        englishDayName: 'saturday',
                        registerCount: userAmountData.saturday,
                      },
                      {
                        englishDayName: 'sunday',
                        registerCount: userAmountData.sunday,
                      },
                    ]}
                  />

                  <HabitDayChart
                    habitList={simpleHabitListData.map((simpleHabit) => {
                      return {
                        habitDayList: simpleHabit.dayList,
                        habitId: simpleHabit.memberHabitId,
                        name: simpleHabit.alias,
                      };
                    })}
                  />
                </div>
              ),
            },
            {
              title: '주기',
              component: (
                <HabitPeriodSelectBox
                  period={period}
                  setPeriod={setPeriod}
                  setDayOfWeek={setDayOfWeek}
                />
              ),
            },
          ]}
        />
      </div>

      <Button
        isActivated={dayOfWeek.length !== 0 || period ? true : false}
        label="완료"
        onClick={
          dayOfWeek.length !== 0 || period ? handleRegisterOverlay : () => {}
        }
      />
    </div>
  );
}
