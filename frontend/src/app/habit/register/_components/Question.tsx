import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import {
  GuideBox,
  Button,
  ProgressBox,
  SelectBox,
  LoadingBottomSheet,
} from '@/components';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-note.json';
import { useState } from 'react';
import { useOverlay } from '@/hooks/use-overlay';

const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

interface IQuestionStepComponent {
  onPrev: () => void;
  onNext: (nextStep: string, isCategorySet: boolean) => void;
}

export default function Question({ onPrev, onNext }: IQuestionStepComponent) {
  const [isAlreadySet, setIsAlreadySet] = useState<string | null>(null);

  const overlay = useOverlay();

  const handleRecommendOverlay = () => {
    overlay.open(({ isOpen }) => (
      <LoadingBottomSheet
        title="최적의 습관을 찾는 중입니다..."
        open={isOpen}
      />
    ));
    setTimeout(() => overlay.close(), 2500);
  };

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
          stages={['추천', '카테고리/별칭 설정', '요일 설정', '완료']}
          beforeStageIndex={-1}
        ></ProgressBox>

        <div className="flex flex-col gap-[0.5rem] items-center">
          <span className="custom-bold-text text-custom-matcha">
            어떤 습관을 지킬지 정하셨나요?
          </span>
          <span className="custom-medium-text">
            {"'네' 를 누르면 바로 카테고리/별칭 설정 단계로,"}
          </span>
          <span className="custom-medium-text">
            {"'아니오' 를 누르면 추천 단계로 넘어가요."}
          </span>
        </div>

        <LottieBox
          loop
          animationData={lottieJson}
          play
          className="w-[15rem] h-[15rem]"
        />

        <SelectBox
          options={[
            { key: 'TRUE', value: '네' },
            { key: 'FALSE', value: '아니오' },
          ]}
          defaultValue={isAlreadySet}
          setDefaultValue={setIsAlreadySet}
        />
      </div>

      <Button
        isActivated={isAlreadySet === null ? false : true}
        label="다음"
        onClick={() => {
          if (isAlreadySet === 'FALSE') {
            handleRecommendOverlay();
            onNext('RECOMMEND_STEP', true);
          } else if (isAlreadySet === 'TRUE') {
            onNext('NICKNAME_STEP', false);
          }
        }}
      />
    </div>
  );
}
