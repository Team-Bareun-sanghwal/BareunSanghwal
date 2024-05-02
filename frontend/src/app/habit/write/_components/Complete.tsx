'use client';

import { Button } from '@/components';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-music.json';

const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

export const Complete = () => {
  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[3rem]">
        <span className="custom-bold-text">해빗 기록 완료</span>

        <div className="flex flex-col items-center gap-[0.5rem]">
          <span className="custom-bold-text">해빗을 기록했어요!</span>
          <span className="custom-medium-text">오늘도 멋지게 해냈네요</span>

          <LottieBox
            loop
            animationData={lottieJson}
            play
            className="w-[15rem] h-[15rem]"
          />

          <span className="custom-light-text">
            기록이 모여서 습관을 만들고, 규칙적인 삶은 만들어요.
          </span>
          <span className="custom-light-text">
            어려운 일을 하게 되더라도 해낼 수 있는 힘을 길러주죠.
          </span>
        </div>
      </div>

      <Button isActivated={true} label="확인" onClick={() => {}} />
    </div>
  );
};
