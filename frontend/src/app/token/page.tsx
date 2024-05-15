'use client';

import { useEffect } from 'react';
import { $SetCookie, $GetRefreshToken } from '@/apis';
import { useRouter } from 'next/navigation';

export default function Page() {
  const router = useRouter();

  const getRefreshToken = async () => {
    const res = await $GetRefreshToken();
    console.log(res);

    if (res.status === 200) {
      await $SetCookie({ at: res.data });
      // router.refresh();
      router.back();
    }
  };

  useEffect(() => {
    getRefreshToken();
  }, []);
}
