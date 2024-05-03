'use server';

import { cookies } from 'next/headers';
import { revalidateTag } from 'next/cache';
import { TinyButton } from '@/components';

async function getData() {
  const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/products`);

  if (!res.ok) {
    // This will activate the closest `error.js` Error Boundary
    throw new Error('Failed to fetch data');
  }

  return res.json();
}

export default async function Page() {
  const cookieStore = cookies();
  const accessToken = cookieStore.get('Authorization');
  const refreshToken = cookieStore.get('RefreshToken');
  // console.log(accessToken);
  // console.log(refreshToken);

  return (
    <>
      <div>우우</div>
      {accessToken ? (
        <div>{accessToken.value}</div>
      ) : (
        <div>어세스 토큰이 업서</div>
      )}
      {refreshToken ? (
        <div>{refreshToken.value}</div>
      ) : (
        <div>리프레시 토큰이 업서</div>
      )}
      {/* {data ? <div>{data}</div> : null} */}
      {/* <TinyButton mode="RECOMMEND" label="우웅 상품 목록" onClick={getData} /> */}
    </>
  );
}
