'use client';

import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { Question } from './Question';
import { Nickname } from './Nickname';

export const HabitRegisterFunnel = () => {
  const { Funnel, setStep } = useFunnel('NICKNAME_STEP'); // 초기 스텝
  const [data, setData] = useState({}); // 누적 데이터
  const router = useRouter();

  return (
    <Funnel>
      <Funnel.Step name="QUESTION_STEP">
        <Question onPrev={() => router.back()} onNext={() => {}} />
      </Funnel.Step>

      <Funnel.Step name="NICKNAME_STEP">
        <Nickname onPrev={() => router.back()} onNext={() => {}} />
      </Funnel.Step>
    </Funnel>
  );
};
