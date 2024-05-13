'use client';

import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';
import { Write } from './Write';
import { Complete } from './Complete';

export const HabitWriteFunnel = ({
  habitTrackerId,
  authorization,
}: {
  habitTrackerId: number;
  authorization?: string;
}) => {
  const { Funnel, setStep } = useFunnel('WRITE_STEP');

  const router = useRouter();

  return (
    <Funnel>
      <Funnel.Step name="WRITE_STEP">
        <Write
          onPrev={() => router.back()}
          onNext={() => {
            setStep('COMPLETE_STEP');
          }}
          habitTrackerId={habitTrackerId}
          authorization={authorization}
        />
      </Funnel.Step>

      <Funnel.Step name="COMPLETE_STEP">
        <Complete onNext={() => router.push('/habit')} />
      </Funnel.Step>
    </Funnel>
  );
};
