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
    isCategorySet: false,
    alias: null,
    icon: null,
  });
  const router = useRouter();

  console.log(data);

  return (
    <Funnel>
      <Funnel.Step name="QUESTION_STEP">
        <Question
          onPrev={() => router.back()}
          onNext={(nextStep, isCategorySet) => {
            setStep(nextStep);
            setData({ ...data, isCategorySet: isCategorySet });
          }}
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
        />
      </Funnel.Step>

      <Funnel.Step name="NICKNAME_STEP">
        <Nickname
          onPrev={() => setStep('QUESTION_STEP')}
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
