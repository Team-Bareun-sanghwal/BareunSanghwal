import { RequestCookies } from 'next/dist/compiled/@edge-runtime/cookies';
import { cookies } from 'next/headers';

export default function Page() {
  const cookieStore = cookies();
  const accessToken = cookieStore.get('Authorization');
  const refreshToken = cookieStore.get('RefreshToken');
  console.log(accessToken);
  console.log(refreshToken);

  return (
    <>
      <div>우우</div>
      {accessToken ? <div>{accessToken}</div> : <div>어세스 토큰이 업서</div>}
      {refreshToken ? (
        <div>{refreshToken}</div>
      ) : (
        <div>리프레시 토큰이 업서</div>
      )}
    </>
  );
}
