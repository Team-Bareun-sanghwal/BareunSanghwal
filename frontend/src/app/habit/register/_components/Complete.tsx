'use client';

import { Button, ProgressBox } from '@/components';
import { IFunnelComponent } from '../_types';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-music.json';

const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

export const Complete = ({ onPrev, onNext }: IFunnelComponent) => {
  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[3rem] pb-[2rem]">
        <span className="custom-bold-text">신규 해빗 등록 완료</span>

        <ProgressBox
          stages={['추천', '카테고리/별칭 설정', '요일 설정', '완료']}
          beforeStageIndex={3}
        ></ProgressBox>

        <div className="flex flex-col items-center gap-[0.5rem]">
          <span className="custom-bold-text">해빗을 새롭게 등록했어요!</span>
          <span className="custom-medium-text">
            해빗 기록은 내일부터 활성화돼요
          </span>

          <LottieBox
            loop
            animationData={lottieJson}
            play
            className="w-[15rem] h-[15rem]"
          />

          <span className="custom-light-text">
            좋은 습관은 당신에게 더 좋은 삶을 선물해 줄 거에요
          </span>
          <span className="custom-light-text">
            지금 이 마음가짐으로 쭉 나아가시길 바랄게요!
          </span>
        </div>
      </div>

      <Button isActivated={true} label="확인" onClick={onNext} />
    </div>
  );
};
