import { LoginButton } from '@/components';
import Image from 'next/image';

export default function Page() {
  return (
    <div className="bg-custom-matcha w-full h-screen">
      <div className="w-[10rem] absolute top-[20rem] right-[4rem] text-center flex flex-col gap-[1rem]">
        <div className="text-white w-full custom-medium-text flex justify-center absolute logo-desc">
          <p>오늘도,</p>
          <span className="w-[1.5rem]" />
          <p>쌓자!</p>
        </div>
        <div className="absolute w-full logo-bareun-life text-center top-[3rem]">
          <div className="text-white custom-logo-text">바른</div>
          <div className="text-white custom-logo-text">생활</div>
        </div>
      </div>

      <div className="flex flex-col relative">
        <Image
          src="/images/icon-block4.png"
          alt="block4"
          width={230}
          height={230}
          className="absolute top-[30rem] loading-block-white"
        />
        <Image
          src="/images/icon-block3.png"
          alt="block3"
          width={230}
          height={230}
          className="absolute top-[23.7rem] loading-block-blue"
        />
        <Image
          src="/images/icon-block2.png"
          alt="block2"
          width={230}
          height={230}
          className="absolute top-[19.1rem] loading-block-yellow"
        />
        <Image
          src="/images/icon-block1.png"
          alt="block1"
          width={230}
          height={230}
          className="absolute top-[12.9rem] loading-block-red"
        />
      </div>

      <div className="bottom-[5rem] w-[36rem] flex flex-col self-center items-center gap-[1rem] absolute loginButtons">
        <LoginButton platform="kakao" />
        <LoginButton platform="google" />
      </div>
    </div>
  );
}
