import { cookies } from 'next/headers';
import { useSearchParams } from 'next/navigation';

export default function Page() {
  const cookieStore = cookies();
  const accessToken = cookieStore.get('Authorization');
  const refreshToken = cookieStore.get('RefreshToken');

  const searchParams = useSearchParams();
  const status = searchParams.get('status');

  return (
    <>
      <div>우우</div>
      {status ? <div>{status.toString()}</div> : '스테이트가 업서'}
      {accessToken ? <div>{accessToken.toString()}</div> : '어세스 토큰이 업서'}
      {refreshToken ? (
        <div>{refreshToken.toString()}</div>
      ) : (
        '리프레시 토큰이 업서'
      )}
    </>
  );
}
