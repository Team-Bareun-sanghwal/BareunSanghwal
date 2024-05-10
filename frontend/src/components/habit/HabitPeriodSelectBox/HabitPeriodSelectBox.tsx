'use client';

import { GuideText } from '@/components/common/GuideText/GuideText';

interface IHabitPeriodSelectBoxProps {
  period: number | null;
  setPeriod: (period: number) => void;
  setDayOfWeek: (emptyArrayValue: number[]) => void;
}

const PeriodButton = ({
  isSelected,
  period,
}: {
  isSelected: boolean;
  period: number;
}) => {
  return (
    <button
      className={`${isSelected ? 'text-custom-white bg-custom-matcha' : 'text-custom-black bg-custom-light-gray'} w-[4rem] h-[4rem] rounded-full custom-medium-text`}
    >
      {period}
    </button>
  );
};

export const HabitPeriodSelectBox = ({
  period,
  setPeriod,
  setDayOfWeek,
}: IHabitPeriodSelectBoxProps) => {
  return (
    <section className="w-full flex flex-col gap-[0.5rem]">
      <label className="custom-semibold-text text-custom-black">
        해빗 주기를 정해주세요
      </label>

      <GuideText text="어느 간격으로 해빗을 기록할지 정해주세요. 단위는 '일'이에요" />

      <ul className="mx-auto list-none flex gap-[1.5rem] mt-[2rem]">
        {[2, 3, 4, 5, 6].map((num, index) => {
          return (
            <li
              key={`period-${index}`}
              onClick={() => {
                setPeriod(num);
                setDayOfWeek([]);
              }}
            >
              <PeriodButton isSelected={period === num} period={num} />
            </li>
          );
        })}
      </ul>
    </section>
  );
};
