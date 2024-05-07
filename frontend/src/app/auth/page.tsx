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

  // if (jsonAccess.body.status === 401) {
  //   console.log('힝힝,,,');

  //   // refresh token 로직
  //   // const jsonRefresh = await $Fetch({
  //   //   method: 'POST',
  //   //   url: `${process.env.NEXT_PUBLIC_BASE_URL}/oauth2/authorization/google`,
  //   //   data: { refreshToken: `${refreshToken}` },
  //   //   cache: 'default',
  //   // });
  // } else {
  //   result = jsonAccess.body.data.products;
  // }
}
