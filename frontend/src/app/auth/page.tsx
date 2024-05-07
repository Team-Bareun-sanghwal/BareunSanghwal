'use client';

import { redirect, useSearchParams } from 'next/navigation';

export default function Page() {
  const searchParams = useSearchParams();
  const code = searchParams.get('status');

  if (code === '200') {
    redirect('/main');
  } else {
    redirect('/signin');
  }
}
