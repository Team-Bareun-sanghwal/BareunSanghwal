import { cookies } from 'next/headers';

export default function Page() {
  const cookieStore = cookies();
  const accessToken = cookieStore.get('Authorization');
  const refreshToken = cookieStore.get('RefreshToken');

  return (
    <>
      <div>우우</div>
      {accessToken ? <div>{accessToken.toString()}</div> : '어세스 토큰이 업서'}
      {refreshToken ? (
        <div>{refreshToken.toString()}</div>
      ) : (
        '리프레시 토큰이 업서'
      )}
    </>
  );
}
