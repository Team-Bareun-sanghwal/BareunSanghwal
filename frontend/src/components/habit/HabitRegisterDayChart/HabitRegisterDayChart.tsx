'use client';

import { motion } from 'framer-motion';
import { GuideText } from '@/components/common/GuideText/GuideText';
import {
  convertEnglishDayNameToIndex,
  convertEnglishDayNameToKoreanDayName,
  getHeightStyle,
} from './utils';

interface IHabitRegisterDayData {
  englishDayName: string;
  registerCount: number;
}

interface IHabitRegisterDayChartProps {
  dayOfWeek: number[];
  setDayOfWeek: (dayOfWeek: number[]) => void;
  setPeriod: (nullValue: null) => void;
  habitRegisterDayList: IHabitRegisterDayData[];
}

const HabitDayBar = ({
  heightRate,
  count,
  koreanDayName,
  isClicked,
  click,
}: {
  heightRate: number;
  count: number;
  koreanDayName: string;
  isClicked: boolean;
  click: () => void;
}) => {
  const heightStyle = getHeightStyle(heightRate);

  return (
    <div
      className="w-full h-[22rem] flex flex-col gap-[0.5rem] items-center px-[0.3rem] pb-[1rem] justify-end cursor-pointer"
      onClick={click}
    >
      <span
        className={`${isClicked ? 'text-custom-matcha' : 'text-custom-medium-gray'} custom-light-text`}
      >
        {isClicked ? count + 1 : count}
      </span>
      <motion.div
        initial={{ height: 0 }}
        animate={{ height: isClicked ? heightStyle + 10 : heightStyle }}
        transition={{ duration: 0.5 }}
        className={`${isClicked ? 'bg-custom-matcha' : 'bg-custom-light-gray'} w-full rounded-[1rem]`}
      ></motion.div>
      <span className="text-custom-black custom-medium-text">
        {koreanDayName}
      </span>
    </div>
  );
};

export const HabitRegisterDayChart = ({
  dayOfWeek,
  setDayOfWeek,
  setPeriod,
  habitRegisterDayList,
}: IHabitRegisterDayChartProps) => {
  const handleSelectedDays = (dayIndex: number) => {
    if (dayOfWeek?.includes(dayIndex)) {
      setDayOfWeek(
        [...dayOfWeek].filter((value: number) => value !== dayIndex),
      );
    } else {
      setDayOfWeek([...dayOfWeek, dayIndex]);
      setPeriod(null);
    }
  };

  const sumOfHabitRegisterCount = habitRegisterDayList.reduce((acc, cur) => {
    return acc + cur.registerCount;
  }, 0);

  return (
    <section className="w-full flex flex-col gap-[0.5rem]">
      <label className="custom-semibold-text text-custom-black">
        해빗을 기록할 요일을 골라주세요
      </label>

      <GuideText text="해당 요일에 얼마나 많은 사용자가 기록하고 있는지 볼 수 있어요" />

      <div className="flex divide-dashed divide-x-[0.1rem] divide-custom-medium-gray">
        {habitRegisterDayList?.map((habit, index) => {
          return (
            <HabitDayBar
              key={`habitRegisterDay-${index}`}
              heightRate={Math.round(
                (habit.registerCount / sumOfHabitRegisterCount) * 100,
              )}
              count={habit.registerCount}
              koreanDayName={convertEnglishDayNameToKoreanDayName(
                habit.englishDayName,
              )}
              isClicked={dayOfWeek.includes(
                convertEnglishDayNameToIndex(habit.englishDayName),
              )}
              click={() =>
                handleSelectedDays(
                  convertEnglishDayNameToIndex(habit.englishDayName),
                )
              }
            />
          );
        })}
      </div>
    </section>
  );
};
