import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { Button, ProgressBox, HabitSearchBox, InputBox } from '@/components';
import { useState } from 'react';
import { Picker } from '@/components/common/Picker/Picker';

interface INicknameStep {
  onPrev: () => void;
  onNext: (
    alias: string,
    icon: string,
    habitId: number | null,
    habitName: string | null,
  ) => void;
  isCategorySet: boolean | null;
  habitId: number | null;
  habitName: string | null;
  alias: string | null;
  icon: string | null;
}

export default function Nickname({
  onPrev,
  onNext,
  isCategorySet,
  habitId,
  habitName,
  alias,
  icon
}: INicknameStep) {
  const [selectedHabitId, setSelectedHabitId] = useState<number | null>(
    habitId,
  );
  const [selectedHabitName, setSelectedHabitName] = useState<string | null>(
    habitName,
  );
  const [selectedAlias, setSelectedAlias] = useState<string | null>(alias);
  const [selectedIcon, setSelectedIcon] = useState<string | null>(icon);

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
            selectedHabitName={selectedHabitName}
            setSelectedHabitId={setSelectedHabitId}
            setSelectedHabitName={setSelectedHabitName}
          />
        )}

        <InputBox
          isLabel={true}
          mode="HABITNICKNAME"
          defaultValue={selectedAlias || ''}
          setDefaultValue={setSelectedAlias}
        />

        <Picker
          label="해빗 아이콘을 골라주세요"
          selectedEmoji={selectedIcon || ''}
          setSelectedEmoji={setSelectedIcon}
        />
      </div>

      <Button
        isActivated={
          isCategorySet
            ? selectedAlias && selectedIcon
              ? true
              : false
            : selectedHabitId && selectedAlias && selectedIcon
              ? true
              : false
        }
        label="다음"
        onClick={
          isCategorySet
            ? selectedAlias && selectedIcon
              ? () => onNext(selectedAlias, selectedIcon, selectedHabitId, selectedHabitName)
              : () => {}
            : selectedHabitId && selectedHabitName && selectedAlias && selectedIcon
              ? () => onNext(selectedAlias, selectedIcon, selectedHabitId, selectedHabitName)
              : () => {}
        }
      />
    </div>
  );
}
