import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { Button, ProgressBox, HabitCategoryList, GuideBox } from '@/components';
import { IFunnelComponent } from '../_types';
import { useState } from 'react';

export const Recommend = ({ onPrev, onNext }: IFunnelComponent) => {
  const [isAlreadySet, setIsAlreadySet] = useState<boolean | null>(null);

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[2rem] pb-[1rem]">
        <nav className="flex self-start gap-[0.5rem] items-center mb-[1rem]">
          <ChevronLeftIcon
            className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray"
            onClick={onPrev}
          />
          <span className="custom-bold-text">신규 해빗 등록</span>
        </nav>

        <ProgressBox
          stages={['추천', '카테고리/별칭 설정', '요일 설정', '완료']}
          beforeStageIndex={0}
        ></ProgressBox>

        <GuideBox guideText="가장 인기 있는 해빗이나 나와 비슷한 사람들이 하는 해빗 중 흥미로운 해빗 하나를 골라주세요." />

        <HabitCategoryList
          habitListData={[
            {
              habitId: 1,
              name: '근력 쌓기',
            },
            {
              habitId: 2,
              name: '운동하기',
            },
            {
              habitId: 3,
              name: '수면 시간 지키기',
            },
            {
              habitId: 4,
              name: '청결 유지하기',
            },
            {
              habitId: 5,
              name: '물 마시기',
            },
            {
              habitId: 6,
              name: '잘 씻기',
            },
            {
              habitId: 7,
              name: '일찍 기상하기',
            },
            {
              habitId: 8,
              name: '축구하기',
            },
            {
              habitId: 9,
              name: '영단어 외우기',
            },
            {
              habitId: 10,
              name: '스트레칭',
            },
          ]}
          label="사람들이 가장 많이 하는 해빗 10개"
          mode="POPULAR"
        />

        <HabitCategoryList
          habitListData={[
            {
              habitId: 1,
              name: '근력 쌓기',
            },
            {
              habitId: 2,
              name: '운동하기',
            },
            {
              habitId: 3,
              name: '수면 시간 지키기',
            },
            {
              habitId: 4,
              name: '청결 유지하기',
            },
            {
              habitId: 5,
              name: '물 마시기',
            },
            {
              habitId: 6,
              name: '잘 씻기',
            },
            {
              habitId: 7,
              name: '일찍 기상하기',
            },
            {
              habitId: 8,
              name: '축구하기',
            },
            {
              habitId: 9,
              name: '영단어 외우기',
            },
            {
              habitId: 10,
              name: '스트레칭',
            },
          ]}
          label="나와 비슷한 사람들이 하는 해빗 10개"
          mode="SIMILAR"
        />
      </div>

      <Button
        isActivated={isAlreadySet === null ? false : true}
        label="다음"
        onClick={onNext}
      />
    </div>
  );
};
