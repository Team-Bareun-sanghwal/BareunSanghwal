'use client';

import { checkPermission } from '@/worker/firebase-messaging-sw';
import { BellIcon } from '@heroicons/react/24/outline';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

export const BellButton = () => {
  const router = useRouter();

  useEffect(() => {
    checkPermission();
  });

  return (
    <BellIcon
      className="w-[2.4rem] h-[2.4rem] text-custom-black"
      onClick={() => {
        router.push('/notification');
      }}
    />
  );
};
