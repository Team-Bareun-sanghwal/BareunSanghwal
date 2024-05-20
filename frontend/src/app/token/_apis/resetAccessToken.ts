'use server';

import { $GetRefreshToken, $SetCookie } from '@/apis';
import { redirect } from 'next/navigation';

export async function resetAccessToken() {
  'use server';

  const res = await $GetRefreshToken();
  const status = res.status;
  if (status === 200) {
    const at = res.data;
    await $SetCookie({ at: at });
  } else {
    redirect('/');
  }
  return status;
}
