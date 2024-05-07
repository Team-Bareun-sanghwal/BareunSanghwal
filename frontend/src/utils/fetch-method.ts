import { cookies } from 'next/headers';

type Request = {
  method: 'GET' | 'POST' | 'PUT' | 'DELETE';
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

  const res = await fetch(url, {
    method,
    cache: cache,
    headers: {
      'Content-Type': 'application/json',
      // 배포 버전
      Authorization: `${authorization}`,

      // 로컬 버전
      // Authorization:
      // 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6IjEzIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTcxNDg5MzQxMCwiZXhwIjoxNzE0ODk0MDEwfQ.hDn0y0SkOWavcBFojXuz3XK6eAFUdOxbrHmiAoBWgRY',
    },
    body: JSON.stringify(data),
  });
  return res.json();

  if (res.ok) {
    return res.json();
  }
  if (!res.ok) {
    return res.status;
  }
}
