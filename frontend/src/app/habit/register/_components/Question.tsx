import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { Button, ProgressBox, SelectBox } from '@/components';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-note.json';
import { IFunnelComponent } from '../_types';
import { useState } from 'react';

const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

export const Question = ({ onPrev, onNext }: IFunnelComponent) => {
  const [isAlreadySet, setIsAlreadySet] = useState<boolean | null>(null);

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col items-center justify-center gap-[3rem]">
        <nav className="flex self-start gap-[0.5rem] items-center">
          <ChevronLeftIcon
            className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray"
            onClick={onPrev}
          />
          <span className="custom-bold-text">신규 해빗 등록</span>
        </nav>

        <ProgressBox
          stages={['추천/등록', '별칭 설정', '요일 설정', '완료']}
          beforeStageIndex={-1}
        ></ProgressBox>

        <label className="custom-bold-text text-custom-matcha">
          어떤 습관을 지킬지 정하셨나요?
        </label>

        <LottieBox
          loop
          animationData={lottieJson}
          play
          className="w-[15rem] h-[15rem]"
        />

        <SelectBox options={['네', '아니오']} />
      </div>

      <Button
        isActivated={isAlreadySet === null ? false : true}
        label="다음"
        onClick={onNext}
      />
    </div>
  );
};
