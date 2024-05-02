'use client';

import { useRouter } from 'next/navigation';
import Image from 'next/image';

interface ILoginButtonProps {
  platform: string;
}

export const LoginButton = ({ platform }: ILoginButtonProps) => {
  const router = useRouter();

  const signIn = () => {
    const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=82281de6e3c9869954034742a037b692&redirect_uri=https://bareun.life/api/login/oauth2/code/kakao&response_type=code`;

    router.push(KAKAO_AUTH_URL);
    // const response = await axios.get(
    //      `${API 서버 주소}/oauth/token?code=${code}`,
    //      { withCredentials: true }
    //    );

    // const url =
    //   platform === 'kakao'
    //     ? process.env.NEXT_PUBLIC_OAUTH_KAKAO_URL!
    //     : process.env.NEXT_PUBLIC_OAUTH_GOOGLE_URL!;
    // router.push(url);
    // console.log(1);
    // console.log(2);
    // console.log(3);
  };

  const bgColor = platform === 'kakao' ? 'bg-custom-kakao' : 'bg-custom-google';

  return (
    <button
      onClick={signIn}
      className={`w-[34rem] h-[3rem] ${bgColor} rounded-[0.8rem] flex items-center justify-center`}
    >
      <Image
        src={`/images/icon-login-${platform}.png`}
        width={10}
        height={10}
        alt={`${platform}-logo`}
      />
      <p className={`custom-login-text pl-2`}>
        {platform === 'kakao' ? '카카오' : '구글'}로 함께하기
      </p>
    </button>
  );
};
