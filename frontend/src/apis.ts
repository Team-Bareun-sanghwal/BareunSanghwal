'use server';

import { cookies } from 'next/headers';
import { redirect } from 'next/navigation';

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

export async function $SetCookie({ at, rt }: { at: string; rt?: string }) {
  const cookieStore = cookies();
  cookieStore.set('Authorization', at, { maxAge: 1000000 });
  if (rt) {
    cookieStore.set('RefreshToken', rt);
  }
}

export async function $Fetch({ method, url, data, cache }: Request) {
  const cookieStore = cookies();
  // const authorization = cookieStore.get('Authorization')?.value;
  const authorization = process.env.NEXT_PUBLIC_ACCESS_TOKEN;
  const refreshToken = cookieStore.get('RefreshToken')?.value;

  if (authorization !== undefined) {
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
        case 500:
          console.log('서버 오류 발생');
          break;
      }
      return await json;
    } catch (e) {
      console.log('Fetch Error : ', e);
      throw e;
    }
  } else {
    redirect('/token');
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

export async function $Logout() {
  const cookieStore = cookies();
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
      redirect('/');
    }

    return res.json();
  } catch (e) {
    console.log('Fetch Error : ', e);
    throw e;
  }
}
