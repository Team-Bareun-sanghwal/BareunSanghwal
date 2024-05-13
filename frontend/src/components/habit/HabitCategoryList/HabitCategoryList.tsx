'use client';

import { TrophyIcon } from '@heroicons/react/24/solid';
import { TinyButton } from '@/components/common/TinyButton/TinyButton';
import { IHabitListData } from '@/app/habit/_types';

interface IHabitCategoryListProps {
  mode: 'POPULAR' | 'SIMILAR';
  label: string;
  habitListData: IHabitListData[];
  selectedHabitId: number | null;
  setSelectedHabitId: (habitId: number) => void;
  setSelectedHabitName: (habitName: string) => void;
}

export const HabitCategoryList = ({
  mode,
  label,
  habitListData,
  selectedHabitId,
  setSelectedHabitId,
  setSelectedHabitName,
}: IHabitCategoryListProps) => {
  return (
    <section className="flex flex-col gap-[1rem]">
      <label className="w-full custom-semibold-text text-custom-black flex justify-between">
        <>{label}</>
        {mode === 'SIMILAR' && (
          <TinyButton mode="RECOMMEND" label="다시 추천" />
        )}
      </label>

      <div className="w-full flex gap-[1rem] flex-wrap">
        {habitListData.map((data, index) => {
          const trophyColor =
            index === 0
              ? 'text-[#d5a11e]'
              : index === 1
                ? 'text-[#a3a3a3]'
                : 'text-[#cd7f32]';

          const outlineColor =
            index === 0
              ? 'outline-[#d5a11e]'
              : index === 1
                ? 'outline-[#a3a3a3]'
                : 'outline-[#cd7f32]';

          return (
            <button
              key={`habitList-${index}`}
              className={`${mode === 'POPULAR' && selectedHabitId !== data.habitId && (index === 0 || index === 1 || index === 2) && `outline-dashed ${outlineColor} outline-[0.1rem]`} ${selectedHabitId === data.habitId ? 'bg-custom-matcha text-custom-white' : 'bg-custom-light-gray text-custom-black'} min-w-fit h-[3.4rem] px-[1rem] py-[0.5rem] rounded-[1rem] custom-medium-text flex items-center gap-[0.5rem]`}
              onClick={() => {
                setSelectedHabitId(data.habitId);
                setSelectedHabitName(data.name);
              }}
            >
              <>
                {mode === 'POPULAR' &&
                  (index === 0 || index === 1 || index === 2) && (
                    <TrophyIcon
                      className={`${trophyColor} w-[2rem] h-[2rem]`}
                    />
                  )}
              </>
              {mode === 'POPULAR' && (
                <span className="font-bold">{`${index + 1}위`}</span>
              )}
              <span>{data.name}</span>
            </button>
          );
        })}
      </div>
    </section>
  );
};