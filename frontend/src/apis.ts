'use server';

import { cookies } from 'next/headers';

type Request = {
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH';
  url: string;
  data?: object | FormData;
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

    return await res.json();
  } catch (e) {
    console.log('Fetch Error : ', e);
    throw e;
  }
}