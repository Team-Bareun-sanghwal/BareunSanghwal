'use client';

export default function Page() {
  let code = new URL(window.location.href).searchParams.get('code');
  const url = `${process.env.NEXT_PUBLIC_BASE_URL}/oauth2/authorization/kakao?code=${code}`;

  const logIn = async () => {
    const data = await (await fetch(url)).json();
    console.log(data);
  };
  logIn();

  return (
    <div>
      <div>칵카오</div>
      <p>{code}</p>
    </div>
  );
}
