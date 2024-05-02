'use client';

export default function Page() {
  let code = new URL(window.location.href).searchParams.get('code');
  const url = `https://bareun.life/api/oauth2/authorization/kakao?code=${code}`;
  // const url = `${process.env.NEXT_PUBLIC_BASE_URL}/oauth2/authorization/kakao?code=${code}`;

  const logIn = () => {
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json',
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error(error);
      });
  };
  logIn();

  return (
    <div>
      <div>칵카오</div>
      <p>{code}</p>
    </div>
  );
}
