'use client';

import dynamic from 'next/dynamic';

import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import {
  IHabitListData,
  IRegisteredHabitData,
  ISimpleHabitListData,
} from '../../_types';
import { IUserAmountData } from '../../_types';

const Question = dynamic(() => import('./Question'));
const Recommend = dynamic(() => import('./Recommend'));
const Nickname = dynamic(() => import('./Nickname'));
const DayOrPeriod = dynamic(() => import('./DayOrPeriod'));
const Complete = dynamic(() => import('./Complete'));

export const HabitRegisterFunnel = ({
  popularCategoryListData,
  similarCategoryListData,
  userAmountData,
  simpleHabitListData,
}: {
  popularCategoryListData: IHabitListData[];
  similarCategoryListData: IHabitListData[];
  userAmountData: IUserAmountData;
  simpleHabitListData: ISimpleHabitListData[];
}) => {
  const { Funnel, setStep } = useFunnel('QUESTION_STEP');
  const [data, setData] = useState<IRegisteredHabitData>({
    habitId: null,
    habitName: null,
    isCategorySet: null,
    alias: null,
    icon: null,
  });
  const router = useRouter();

  // 분기 처리

  // 추천에서 네 -> (카테고리, 별칭, 아이콘) -> (요일 | 주기, 이 부분은 원래 저장하지 않음)
  // 추천에서 아니오 -> (카테고리) -> (별칭, 아이콘) -> (요일 | 주기, 이 부분은 원래 저장하지 않음)
  // 네 아니오 부분도 저장을 해놓되, 이전과 값이 달라지면 뒤에 값도 모두 초기화?
  //

  return (
    <Funnel>
      <Funnel.Step name="QUESTION_STEP">
        <Question
          onPrev={() => router.back()}
          onNext={(nextStep, isCategorySet) => {
            setStep(nextStep);
            if (data.isCategorySet !== isCategorySet)
              setData({
                habitId: null,
                habitName: null,
                isCategorySet: isCategorySet,
                alias: null,
                icon: null,
              });
            else setData({ ...data, isCategorySet: isCategorySet });
          }}
          isCategorySet={data.isCategorySet}
        />
      </Funnel.Step>

      <Funnel.Step name="RECOMMEND_STEP">
        <Recommend
          onPrev={() => setStep('QUESTION_STEP')}
          onNext={(habitId, habitName) => {
            setStep('NICKNAME_STEP');
            setData({ ...data, habitId: habitId, habitName: habitName });
          }}
          popularCategoryListData={popularCategoryListData}
          similarCategoryListData={similarCategoryListData}
          habitId={data.habitId}
          habitName={data.habitName}
        />
      </Funnel.Step>

      <Funnel.Step name="NICKNAME_STEP">
        <Nickname
          onPrev={() => {
            if (data.isCategorySet) setStep('RECOMMEND_STEP');
            else setStep('QUESTION_STEP');
          }}
          onNext={(alias, icon, habitId, habitName) => {
            setStep('DAYORPERIOD_STEP');
            setData({
              ...data,
              alias: alias,
              icon: icon,
              habitId: habitId,
              habitName: habitName,
            });
          }}
          isCategorySet={data.isCategorySet}
          habitId={data.habitId}
          habitName={data.habitName}
          alias={data.alias}
          icon={data.icon}
        />
      </Funnel.Step>

      <Funnel.Step name="DAYORPERIOD_STEP">
        <DayOrPeriod
          onPrev={() => setStep('NICKNAME_STEP')}
          onNext={() => {
            setStep('COMPLETE_STEP');
          }}
          userAmountData={userAmountData}
          simpleHabitListData={simpleHabitListData}
          data={data}
        />
      </Funnel.Step>

      <Funnel.Step name="COMPLETE_STEP">
        <Complete onNext={() => router.push('/habit')} />
      </Funnel.Step>
    </Funnel>
  );
};
