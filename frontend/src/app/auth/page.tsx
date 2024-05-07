import { cookies } from 'next/headers';
import { TinyButton } from '@/components';
import { $Fetch } from '@/apis';
import { redirect } from 'next/navigation';

export default async function Page() {
  const cookieStore = cookies();
  const authorization = cookieStore.get('Authorization')?.value;
  const refreshToken = cookieStore.get('RefreshToken')?.value;
  // let result;

  const jsonAccess = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products`,
    cache: 'default',
  });
  console.log(jsonAccess);
  if ((await jsonAccess.status) === 200) {
    // result = await jsonAccess.body.data.products;
    // redirect('/tree');
  } else {
    // redirect('/signin');
  }

  // if (jsonAccess.body.status === 401) {
  //   console.log('힝힝,,,');

  //   // refresh token 로직
  //   // const jsonRefresh = await $Fetch({
  //   //   method: 'POST',
  //   //   url: `${process.env.NEXT_PUBLIC_BASE_URL}/oauth2/authorization/google`,
  //   //   data: { refreshToken: `${refreshToken}` },
  //   //   cache: 'default',
  //   // });
  // } else {
  //   result = jsonAccess.body.data.products;
  // }

  return (
    <>
      <div>{jsonAccess.status}</div>
      <div>{jsonAccess.message}</div>
      <hr />

      {/* {result?.map((r: any) => {
        return <div key={r.name}>{r.name}</div>;
      })}
      <hr /> */}

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
    </>
  );
}
