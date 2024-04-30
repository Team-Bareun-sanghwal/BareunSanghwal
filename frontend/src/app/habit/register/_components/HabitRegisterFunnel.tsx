'use client';

import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { Question } from './Question';

export const HabitRegisterFunnel = () => {
  const { Funnel, setStep } = useFunnel('QUESTION_STEP'); // 초기 스텝
  const [data, setData] = useState({}); // 누적 데이터
  const router = useRouter();

  const goToBack = router.back;

  return (
    <Funnel>
      <Funnel.Step name="QUESTION_STEP">
        <Question onPrev={goToBack} onNext={() => {}} />
      </Funnel.Step>

      <Funnel.Step name="QUESTION_STEP">
        <Question onPrev={goToBack} onNext={() => {}} />
      </Funnel.Step>
    </Funnel>
  );
};
