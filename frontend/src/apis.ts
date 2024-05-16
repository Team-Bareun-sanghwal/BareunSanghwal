'use server';

import { cookies } from 'next/headers';
import { RedirectType, redirect } from 'next/navigation';

type Request = {
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH';
  url: string;
  data?: object;
  cache:
    | 'no-cache'
    | 'no-store'
    | 'reload'
    | 'force-cache'
    | 'only-if-cached'
    | 'default';
};

export async function $Fetch({ method, url, data, cache }: Request) {
  const cookieStore = cookies();
  const authorization = process.env.NEXT_PUBLIC_ACCESS_TOKEN;
  // const authorization = cookieStore.get('Authorization')?.value;
  const refreshToken = cookieStore.get('RefreshToken')?.value;
  const res = await fetch(url, {
    method,
    cache: cache,
    headers: {
      'Content-Type': 'application/json',
      Authorization: authorization as string,
      RefreshToken: refreshToken as string,
    },
    body: JSON.stringify(data),
  });

  // const json = await res.json();
  return res.json();

  // if (authorization !== undefined) {
  //   try {
  //     const res = await fetch(url, {
  //       method,
  //       cache: cache,
  //       headers: {
  //         'Content-Type': 'application/json',
  //         Authorization: authorization as string,
  //         RefreshToken: refreshToken as string,
  //       },
  //       body: JSON.stringify(data),
  //     });

  //     const json = await res.json();
  //     switch (json.status) {
  //       case 200:
  //         if (url === `${process.env.NEXT_PUBLIC_BASE_URL}/members/logout`) {
  //           cookieStore.delete('Authorization');
  //           cookieStore.delete('RefreshToken');
  //           redirect('/');
  //         }
  //         break;
  //       case 500:
  //         console.log(url, ' 서버 오류 발생');
  //         break;
  //     }
  //     return json;
  //   } catch (e) {
  //     console.log('Fetch Error : ', e);
  //     throw e;
  //   }
  // } else {
  //   redirect(`/token`, RedirectType.replace);
  // }
}

export async function $SetCookie({ at, rt }: { at: string; rt?: string }) {
  const cookieStore = cookies();
  cookieStore.set('Authorization', at, { maxAge: 7 });
  if (rt) {
    cookieStore.set('RefreshToken', rt);
  }
}

export async function $GetRefreshToken() {
  const cookieStore = cookies();
  const refreshToken = cookieStore.get('RefreshToken')?.value;

  try {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/auth/access-token`,
      {
        method: 'GET',
        cache: 'default',
        headers: {
          'Content-Type': 'application/json',
          RefreshToken: refreshToken as string,
        },
      },
    );

    return await res.json();
  } catch (e) {
    console.log('Fetch Error : ', e);
    throw e;
  }
}
