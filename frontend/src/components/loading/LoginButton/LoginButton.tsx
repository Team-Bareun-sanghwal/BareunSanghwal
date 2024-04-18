'use client';

import { useRouter } from 'next/navigation';
import Image from 'next/image';

interface ILoginButtonProps {
  platform: string;
}

export const LoginButton = ({ platform }: ILoginButtonProps) => {
  const router = useRouter();
  const url =
    platform === 'kakao'
      ? 'https://www.kakaocorp.com/page/'
      : 'https://www.google.co.kr/?hl=ko';

  const bgColor = platform === 'kakao' ? 'bg-custom-kakao' : 'bg-custom-google';

  return (
    <button
      onClick={() => router.push(url)}
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
