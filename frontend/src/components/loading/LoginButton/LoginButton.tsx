'use client';

import { useRouter } from 'next/navigation';
import Image from 'next/image';

interface ILoginButtonProps {
  platform: string;
}

export const LoginButton = ({ platform }: ILoginButtonProps) => {
  const router = useRouter();

  const signIn = async () => {
    const url =
      platform === 'kakao'
        ? process.env.NEXT_PUBLIC_OAUTH_KAKAO_URL!
        : process.env.NEXT_PUBLIC_OAUTH_GOOGLE_URL!;
    router.push(url);
  };

  const bgColor = platform === 'kakao' ? 'bg-custom-kakao' : 'bg-custom-google';

  return (
    <button
      onClick={signIn}
      className={`w-[90%] h-[5rem] ${bgColor} rounded-[0.8rem] flex items-center justify-center`}
    >
      <Image
        src={`/images/icon-login-${platform}.png`}
        width={20}
        height={20}
        alt={`${platform}-logo`}
      />
      <p className={`custom-semibold-text  pl-[1rem]`}>
        {platform === 'kakao' ? '카카오' : '구글'}로 함께하기
      </p>
    </button>
  );
};
