import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { Button, ProgressBox, HabitSearchBox, InputBox } from '@/components';
import { IFunnelComponent } from '../_types';
import { useState } from 'react';
import { Picker } from '@/components/common/Picker/Picker';

export const Nickname = ({ onPrev, onNext }: IFunnelComponent) => {
  const [isAlreadySet, setIsAlreadySet] = useState<boolean | null>(null);

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[3rem]">
        <nav className="flex self-start gap-[0.5rem] items-center">
          <ChevronLeftIcon
            className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray"
            onClick={onPrev}
          />
          <span className="custom-bold-text">신규 해빗 등록</span>
        </nav>

        <ProgressBox
          stages={['추천', '카테고리/별칭 설정', '요일 설정', '완료']}
          beforeStageIndex={1}
        ></ProgressBox>

        <HabitSearchBox
          searchedList={[
            {
              habitId: 1,
              name: '운동하기',
            },
            {
              habitId: 2,
              name: '생활 운동',
            },
          ]}
        />

        <InputBox
          isLabel={true}
          mode="HABITNICKNAME"
          defaultValue=""
          setDefaultValue={() => {}}
        />

        <Picker label="해빗 아이콘을 골라주세요" />
      </div>

      <Button
        isActivated={isAlreadySet === null ? false : true}
        label="다음"
        onClick={onNext}
      />
    </div>
  );
};
