import { useFunnel } from '@/hooks/use-funnel';
import { useRouter } from 'next/navigation';
import { useState } from 'react';

export default function Page() {
  const { Funnel, setStep } = useFunnel('INITIAL_STEP'); // 초기 스텝
  const [data, setData] = useState({}); // 누적 데이터
  const router = useRouter();

  //   return (
  //     <Funnel>
  //       <Funnel.Step name="INITIAL_STEP"></Funnel.Step>
  //     </Funnel>
  //   );
}
