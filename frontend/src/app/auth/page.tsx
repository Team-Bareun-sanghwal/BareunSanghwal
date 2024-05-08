'use client';

import { requestPermission } from '@/worker/firebase-messaging-sw';
import { useRouter, useSearchParams } from 'next/navigation';

export default function Page() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const code = searchParams.get('status');

  if (code === '200') {
    requestPermission();
    router.push('/main');
  } else {
    router.push('/signin');
  }
}
