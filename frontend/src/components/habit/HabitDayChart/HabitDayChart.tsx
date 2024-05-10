'use client';

import { useState } from 'react';
import { GuideText } from '@/components';

interface IHabitDayData {
  name: string;
  habitId: number;
  habitDayList: number[];
}

interface IHabitDayChartProps {
  habitList: IHabitDayData[];
}

const HabitDayBox = ({
  isChecked,
  isFocused,
}: {
  isChecked: boolean;
  koreanDayName: string;
  isFocused: boolean;
}) => {
  return (
    <div className="w-full h-[2rem] my-auto px-[0.3rem] py-[0.4rem]">
      {isChecked && (
        <div
          className={`${isFocused ? 'bg-custom-light-green' : 'bg-custom-light-gray'} w-full h-full rounded-[1rem] text-[1rem] font-semibold flex items-center justify-center`}
        ></div>
      )}
    </div>
  );
};

const HabitDayRow = ({
  dayList,
  isFocused,
}: {
  name: string;
  dayList: number[];
  isFocused: boolean;
}) => {
  return (
    <div className="flex flex-col gap-[0.5rem]">
      <div className="flex divide-dashed divide-x-[0.1rem] divide-custom-medium-gray">
        <HabitDayBox
          isChecked={dayList.includes(1)}
          isFocused={isFocused}
          koreanDayName="월"
        />
        <HabitDayBox
          isChecked={dayList.includes(2)}
          isFocused={isFocused}
          koreanDayName="화"
        />
        <HabitDayBox
          isChecked={dayList.includes(3)}
          isFocused={isFocused}
          koreanDayName="수"
        />
        <HabitDayBox
          isChecked={dayList.includes(4)}
          isFocused={isFocused}
          koreanDayName="목"
        />
        <HabitDayBox
          isChecked={dayList.includes(5)}
          isFocused={isFocused}
          koreanDayName="금"
        />
        <HabitDayBox
          isChecked={dayList.includes(6)}
          isFocused={isFocused}
          koreanDayName="토"
        />
        <HabitDayBox
          isChecked={dayList.includes(7)}
          isFocused={isFocused}
          koreanDayName="일"
        />
      </div>
    </div>
  );
};

export const HabitDayChart = ({ habitList }: IHabitDayChartProps) => {
  const [selectedHabitId, setSelectedHabitId] = useState<number | null>(null);

  return (
    <section className="w-full flex flex-col gap-[0.5rem]">
      <label className="custom-semibold-text text-custom-black">
        현재 진행 중인 해빗의 요일을 확인해요
      </label>

      <GuideText text="내가 등록한 다른 해빗의 요일을 보며 조율해요" />

      {habitList?.map((habit, index) => {
        return (
          <HabitDayRow
            key={`habitDay-${index}`}
            name={habit.name}
            dayList={habit.habitDayList}
            isFocused={
              selectedHabitId ? habit.habitId === selectedHabitId : false
            }
          />
        );
      })}

      <div className="my-[2rem] grid grid-cols-2 mx-auto">
        {habitList?.map((habit, index) => {
          return (
            <div
              key={`habitName-${index}`}
              className="flex gap-[0.5rem] items-center cursor-pointer justify-center px-[1rem] py-[0.5rem]"
              onClick={() => setSelectedHabitId(habit.habitId)}
            >
              <div
                className={`${habit.habitId === selectedHabitId ? 'bg-custom-light-green' : 'bg-custom-medium-gray'} w-[1rem] h-[1rem] rounded-full`}
              ></div>
              <span
                className={`${habit.habitId === selectedHabitId ? 'text-[1.1rem] font-semibold' : 'text-[1.1rem] font-light'}`}
              >
                {habit.name}
              </span>
            </div>
          );
        })}
      </div>
    </section>
  );
};
