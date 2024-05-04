import { cookies } from 'next/headers';
import { TinyButton } from '@/components';
import { RequestCookies } from 'next/dist/compiled/@edge-runtime/cookies';

export default async function Page() {
  const cookieStore = cookies();
  const authorization = cookieStore.get('Authorization')?.value;
  const refreshToken = cookieStore.get('RefreshToken')?.value;
  // console.log(accessToken);
  // console.log(refreshToken);

  async function getData() {
    const res = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/products`, {
      method: 'GET',
      headers: {
        Authorization: `${authorization}`,
        // authorization:
        //   'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6IjEzIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTcxNDgzMDU3NSwiZXhwIjoxNzE0ODMxMTc1fQ._PG2fxeenVolm2O31SdRGmls46y5S7rqwlI6gGEexyc',
      },
    });

    if (!res.ok) {
      // This will activate the closest `error.js` Error Boundary
      throw new Error('Failed to fetch data');
    }

    return res.json();
  }

  const data = await getData();
  const result = data.data.products;
  console.log(data.data.products);

  return (
    <>
      {result.map((r: any) => {
        return <div key={r.name}>{r.name}</div>;
      })}
      <div>우우</div>
      {authorization ? (
        <div>{authorization}</div>
      ) : (
        <div>어세스 토큰이 업서</div>
      )}
      {refreshToken ? (
        <div>{refreshToken}</div>
      ) : (
        <div>리프레시 토큰이 업서</div>
      )}
      {/* {data ? <div>{data}</div> : null} */}
      {/* <TinyButton mode="RECOMMEND" label="우웅 상품 목록" onClick={getData} /> */}
    </>
  );
}
