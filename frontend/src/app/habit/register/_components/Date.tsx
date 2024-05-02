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
} from '@/components';
import { IFunnelComponent } from '../_types';
import { useState } from 'react';

export const Date = ({ onPrev, onNext }: IFunnelComponent) => {
  const [isAlreadySet, setIsAlreadySet] = useState<boolean | null>(null);

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[2rem]">
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
                <div className="flex flex-col gap-[1rem]">
                  <HabitRegisterDayChart
                    habitRegisterDayList={[
                      {
                        englishDayName: 'monday',
                        registerCount: 1024,
                      },
                      {
                        englishDayName: 'tuesday',
                        registerCount: 2048,
                      },
                      {
                        englishDayName: 'wednesday',
                        registerCount: 3024,
                      },
                      {
                        englishDayName: 'thursday',
                        registerCount: 114,
                      },
                      {
                        englishDayName: 'friday',
                        registerCount: 1540,
                      },
                      {
                        englishDayName: 'saturday',
                        registerCount: 1223,
                      },
                      {
                        englishDayName: 'sunday',
                        registerCount: 15,
                      },
                    ]}
                  />
                  <HabitDayChart
                    habitList={[
                      {
                        habitDayList: [2, 4, 7],
                        habitId: 1,
                        name: '물 2L 마시기',
                      },
                      {
                        habitDayList: [3, 4, 6],
                        habitId: 2,
                        name: '프로틴 주스 마시기',
                      },
                    ]}
                  />
                </div>
              ),
            },
            {
              title: '주기',
              component: <HabitPeriodSelectBox />,
            },
          ]}
        />
      </div>

      <Button
        isActivated={isAlreadySet === null ? false : true}
        label="다음"
        onClick={onNext}
      />
    </div>
  );
};
