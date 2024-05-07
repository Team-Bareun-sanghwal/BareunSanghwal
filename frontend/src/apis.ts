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
export async function $Fetch({ method, url, data, cache }: Request) {
  const cookieStore = cookies();
  const authorization = cookieStore.get('Authorization')?.value;
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
    // if (res.status != 200) {
    //   throw new Error(`[HTTP ERR] Status : ${res.status}`);
    // }
    return res.json();
  } catch (e) {
    console.log('Fetch Error : ', e);
    throw e;
  }
}
