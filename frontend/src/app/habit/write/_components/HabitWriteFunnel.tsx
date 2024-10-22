'use client';

import dynamic from 'next/dynamic';
import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';

const Write = dynamic(() => import('./Write'));
const Complete = dynamic(() => import('./Complete'));

export const HabitWriteFunnel = ({
  habitTrackerId,
  authorization,
}: {
  habitTrackerId: number;
  authorization?: string;
}) => {
  const { Funnel, setStep } = useFunnel('WRITE_STEP');

  const router = useRouter();
  router.push(sessionStorage.getItem('previousUrl')!);
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
