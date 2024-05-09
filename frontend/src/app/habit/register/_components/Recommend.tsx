import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { Button, ProgressBox, HabitCategoryList, GuideBox } from '@/components';
import { useState } from 'react';
import { IHabitListData } from '../../_types';

interface IRecommendStep {
  onPrev: () => void;
  onNext: (selectedHabitId: number, selectedHabitName: string) => void;
  popularCategoryListData: IHabitListData[];
  similarCategoryListData: IHabitListData[];
}

export default function Recommend({
  onPrev,
  onNext,
  popularCategoryListData,
  similarCategoryListData,
}: IRecommendStep) {
  const [selectedHabitId, setSelectedHabitId] = useState<number | null>(null);
  const [selectedHabitName, setSelectedHabitName] = useState<string | null>(
    null,
  );

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[2rem] pb-[2rem]">
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
          habitListData={popularCategoryListData}
          label="사람들이 가장 많이 하는 해빗 10개"
          mode="POPULAR"
          selectedHabitId={selectedHabitId}
          setSelectedHabitId={setSelectedHabitId}
          setSelectedHabitName={setSelectedHabitName}
        />

        <HabitCategoryList
          habitListData={similarCategoryListData}
          label="나와 비슷한 사람들이 하는 해빗 10개"
          mode="SIMILAR"
          selectedHabitId={selectedHabitId}
          setSelectedHabitId={setSelectedHabitId}
          setSelectedHabitName={setSelectedHabitName}
        />
      </div>

      <Button
        isActivated={
          selectedHabitId !== null && selectedHabitName !== null ? true : false
        }
        label="다음"
        onClick={
          selectedHabitId !== null && selectedHabitName !== null
            ? () => onNext(selectedHabitId, selectedHabitName)
            : () => {}
        }
      />
    </div>
  );
}
