'use client';

import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-relax.json';
const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

export const RecapFinish = ({ month }: { month: number }) => {
  return (
    <div className="w-[34rem] h-full flex flex-col items-center justify-end">
      <div className="h-[6rem] text-center flex flex-col justify-evenly mb-[1rem]">
        <p className="text-white custom-bold-text">{month}월도 참 잘했어요!</p>
        <p className="text-white custom-light-text">
          바른생활이 앞으로도 응원할게요!
        </p>
      </div>
      <LottieBox loop animationData={lottieJson} play className="w-[36rem]" />
    </div>
  );
};
