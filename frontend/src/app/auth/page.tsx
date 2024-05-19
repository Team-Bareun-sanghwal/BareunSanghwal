'use client';

import { useRouter, useSearchParams } from 'next/navigation';
// import { $SetCookie } from '@/apis';

export default function Page() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const code = searchParams.get('status');
  // const at = searchParams.get('at');
  // const rt = searchParams.get('rt');

  if (code === '200') {
    // $SetCookie({ at: at!, rt: rt! });
    router.push(`/main`);
  } else {
    router.push('/signin');
  }
}
