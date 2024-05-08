'use client';

import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { useRouter } from 'next/navigation';

export const BackToHomeButton = () => {
  const router = useRouter();

  return (
    <ChevronLeftIcon
      className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray"
      onClick={() => {
        router.push('/main');
      }}
    />
  );
};
