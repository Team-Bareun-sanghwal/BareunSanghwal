'use server';

import { cookies } from 'next/headers';

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

// export async function $SetCookie({ at, rt }: { at: string; rt: string }) {
//   const cookieStore = cookies();
//   cookieStore.set('Authorization', at, { maxAge: 10 });
//   cookieStore.set('RefreshToken', rt);
// }

export async function $Fetch({ method, url, data, cache }: Request) {
  const cookieStore = cookies();
  // const authorization = process.env.NEXT_PUBLIC_ACCESS_TOKEN;
  const authorization = cookieStore.get('Authorization')?.value;

  if (authorization) {
    try {
      const res = await fetch(url, {
        method,
        cache: cache,
        headers: {
          'Content-Type': 'application/json',
          Authorization: authorization as string,
        },
        body: JSON.stringify(data),
      });

      const json = await res.json();
      switch ((await json).status) {
        case 200:
          console.log('정상처리');
          return await json;
        case 500:
          console.log('서버 오류 발생');
          break;
      }
    } catch (e) {
      console.log('Fetch Error : ', e);
      throw e;
    }
  } else {
    console.log('Access Token 만료');

    const result = await $GetRefreshToken();
    if ((await result) === 200) {
      console.log('Access Token 재발급 성공');
      await $Fetch({ method, url, data, cache });
    } else {
      console.log('Access Token 재발급 실패');
      window.location.href = '/';
    }
  }
}

export async function $GetRefreshToken() {
  const cookieStore = cookies();
  // const refreshToken = process.env.NEXT_PUBLIC_REFRESH_TOKEN;
  const refreshToken = cookieStore.get('RefreshToken')?.value;
  console.log(refreshToken);

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

    return (await res.json()).status;
  } catch (e) {
    console.log('Fetch Error : ', e);
    throw e;
  }
}

export async function $Logout() {
  const cookieStore = cookies();
  // const authorization = process.env.NEXT_PUBLIC_ACCESS_TOKEN;
  // const refreshToken = process.env.NEXT_PUBLIC_REFRESH_TOKEN;
  const authorization = cookieStore.get('Authorization')?.value;
  const refreshToken = cookieStore.get('RefreshToken')?.value;

  try {
    const res = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/members/logout`,
      {
        method: 'POST',
        cache: 'default',
        headers: {
          'Content-Type': 'application/json',
          Authorization: authorization as string,
          RefreshToken: refreshToken as string,
        },
      },
    );

    if ((await res.json()).status === 200) {
      cookieStore.delete('Authorization');
      cookieStore.delete('RefreshToken');
      window.location.href = '/';
    }

    return await res.json();
  } catch (e) {
    console.log('Fetch Error : ', e);
    throw e;
  }
}
