'use client';

import { BellIcon } from '@heroicons/react/24/outline';
import { useRouter } from 'next/navigation';

export const BellButton = () => {
  const router = useRouter();

  return (
    <BellIcon
      className="w-[2.4rem] h-[2.4rem] text-custom-black"
      onClick={() => {
        router.push('/notification');
      }}
    />
  );
};
