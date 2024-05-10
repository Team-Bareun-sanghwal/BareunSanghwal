import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { Button, ProgressBox, HabitSearchBox, InputBox } from '@/components';
import { useState } from 'react';
import { Picker } from '@/components/common/Picker/Picker';

interface INicknameStep {
  onPrev: () => void;
  onNext: (
    alias: string,
    icon: string,
    selectedHabitId: number | null,
    selectedHabitName: string | null,
  ) => void;
  isCategorySet: boolean;
  habitId: number | null;
  habitName: string | null;
}

export default function Nickname({
  onPrev,
  onNext,
  isCategorySet,
  habitId,
  habitName,
}: INicknameStep) {
  const [selectedHabitId, setSelectedHabitId] = useState<number | null>(
    habitId,
  );
  const [selectedHabitName, setSelectedHabitName] = useState<string | null>(
    habitName,
  );
  const [alias, setAlias] = useState<string | null>(null);
  const [icon, setIcon] = useState<string | null>(null);

  return (
    <div className="min-h-screen p-[1rem] flex flex-col justify-between">
      <div className="w-full flex flex-col gap-[3rem] pb-[2rem]">
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

        {!isCategorySet && (
          <HabitSearchBox
            selectedHabitId={selectedHabitId}
            setSelectedHabitId={setSelectedHabitId}
            setSelectedHabitName={setSelectedHabitName}
          />
        )}

        <InputBox
          isLabel={true}
          mode="HABITNICKNAME"
          defaultValue=""
          setDefaultValue={setAlias}
        />

        <Picker
          label="해빗 아이콘을 골라주세요"
          selectedEmoji={icon}
          setSelectedEmoji={setIcon}
        />
      </div>

      <Button
        isActivated={
          isCategorySet
            ? alias && icon
              ? true
              : false
            : selectedHabitId && alias && icon
              ? true
              : false
        }
        label="다음"
        onClick={
          isCategorySet
            ? alias && icon
              ? () => onNext(alias, icon, selectedHabitId, selectedHabitName)
              : () => {}
            : selectedHabitId && selectedHabitName && alias && icon
              ? () => onNext(alias, icon, selectedHabitId, selectedHabitName)
              : () => {}
        }
      />
    </div>
  );
}
