'use client';

import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { Question } from './Question';
import { Recommend } from './Recommend';
import { Nickname } from './Nickname';
import { DayOrPeriod } from './DayOrPeriod';
import { Complete } from './Complete';

export const HabitRegisterFunnel = () => {
  const { Funnel, setStep } = useFunnel('RECOMMEND_STEP'); // 초기 스텝
  const [data, setData] = useState({}); // 누적 데이터
  const router = useRouter();

  return (
    <Funnel>
      <Funnel.Step name="QUESTION_STEP">
        <Question onPrev={() => router.back()} onNext={() => {}} />
      </Funnel.Step>

      <Funnel.Step name="RECOMMEND_STEP">
        <Recommend onPrev={() => router.back()} onNext={() => {}} />
      </Funnel.Step>

      <Funnel.Step name="NICKNAME_STEP">
        <Nickname onPrev={() => router.back()} onNext={() => {}} />
      </Funnel.Step>

      <Funnel.Step name="DATE_STEP">
        <DayOrPeriod onPrev={() => router.back()} onNext={() => {}} />
      </Funnel.Step>

      <Funnel.Step name="COMPLETE_STEP">
        <Complete onPrev={() => router.back()} onNext={() => {}} />
      </Funnel.Step>
    </Funnel>
  );
};
