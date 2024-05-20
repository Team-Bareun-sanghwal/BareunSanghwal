'use client';

import { getMonth, getYear } from '@/components/calendar/util';
import { ArrowLeftCircleIcon } from '@heroicons/react/24/solid';
import { useRouter } from 'next/navigation';

export const RouteHome = () => {
  const router = useRouter();

  const exit = () => {
    close();
    router.push(`/main`);
  };

  return (
    <>
      <button onClick={exit} className="absolute z-20 p-2 text-lg">
        <ArrowLeftCircleIcon color="white" className="w-[44px] h-[44px]" />
      </button>
    </>
  );
};
