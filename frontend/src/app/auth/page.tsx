'use client';

import { useRouter, useSearchParams } from 'next/navigation';
import { getMonth, getYear } from '@/components/calendar/util';
export default function Page() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const code = searchParams.get('status');

  if (code === '200') {
    router.push(`/main/${getYear()}/${getMonth(true)}`);
  } else {
    router.push('/signin');
  }
}
