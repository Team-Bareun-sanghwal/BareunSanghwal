'use client';

import { useRouter } from 'next/navigation';
import Image from 'next/image';
import { clickPushHandler } from '@/worker/firebase-messaging-sw';

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

  // const signIn = () => {
  //   if (platform === 'kakao') {
  //     const url = process.env.NEXT_PUBLIC_OAUTH_KAKAO_URL!;
  //     router.push(url);
  //   } else {
  //     clickPushHandler();
  //   }
  // };

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
