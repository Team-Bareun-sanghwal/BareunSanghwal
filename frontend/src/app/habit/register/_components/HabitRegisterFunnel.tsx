'use client';

import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { Question } from './Question';
import { Recommend } from './Recommend';
import { Nickname } from './Nickname';
import { DayOrPeriod } from './DayOrPeriod';
import { Complete } from './Complete';
import { IHabitListData } from '../../_types';

export const HabitRegisterFunnel = ({
  popularCategoryListData,
  similarCategoryListData,
}: {
  popularCategoryListData: IHabitListData[];
  similarCategoryListData: IHabitListData[];
}) => {
  const { Funnel, setStep } = useFunnel('QUESTION_STEP'); // 초기 스텝
  const [data, setData] = useState({}); // 누적 데이터
  const router = useRouter();

  console.log(data);

  return (
    <Funnel>
      <Funnel.Step name="QUESTION_STEP">
        <Question
          onPrev={() => router.back()}
          onNext={(nextStep) => {
            setStep(nextStep);
          }}
        />
      </Funnel.Step>

      <Funnel.Step name="RECOMMEND_STEP">
        <Recommend
          onPrev={() => setStep('QUESTION_STEP')}
          onNext={(selectedHabitId) => {
            setStep('NICKNAME_STEP');
            setData({ ...data, habitId: selectedHabitId });
          }}
          popularCategoryListData={popularCategoryListData}
          similarCategoryListData={similarCategoryListData}
        />
      </Funnel.Step>

      <Funnel.Step name="NICKNAME_STEP">
        <Nickname
          onPrev={() => setStep('QUESTION_STEP')}
          onNext={(alias, icon) => {
            setStep('DAYORPERIOD_STEP');
            setData({ ...data, alias: alias, icon: icon });
          }}
          isCategorySet={data.hasOwnProperty('habitId')}
        />
      </Funnel.Step>

      <Funnel.Step name="DAYORPERIOD_STEP">
        <DayOrPeriod
          onPrev={() => setStep('NICKNAME_STEP')}
          onNext={() => {
            setStep('COMPLETE_STEP');
          }}
        />
      </Funnel.Step>

      <Funnel.Step name="COMPLETE_STEP">
        <Complete onPrev={() => {}} onNext={() => router.push('/habit')} />
      </Funnel.Step>
    </Funnel>
  );
};
