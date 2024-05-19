'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { resetAccessToken } from './_apis/resetAccessToken';

export default function Page() {
  const router = useRouter();

  useEffect(() => {
    resetAccessToken();
  }, []);

  router.push(sessionStorage.getItem('previousUrl')!);
}
