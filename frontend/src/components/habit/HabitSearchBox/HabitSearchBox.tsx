'use client';

import { useState } from 'react';
import { IHabitListDataV2 } from '@/app/habit/_types';
import { searchCategoryList } from '@/app/habit/_apis/searchCategoryList';
import { GuideText } from '@/components/common/GuideText/GuideText';

const HabitNameButton = ({
  name,
  isSelected,
  onClick,
}: {
  name: string;
  isSelected: boolean;
  onClick: () => void;
}) => {
  return (
    <button
      onClick={onClick}
      className={`${isSelected ? 'bg-custom-matcha text-custom-white' : 'bg-custom-light-gray text-custom-black'} custom-medium-text px-[1rem] py-[0.5rem] rounded-[1rem]`}
    >
      {name}
    </button>
  );
};

interface IHabitSearchBox {
  selectedHabitId: number | null;
  selectedHabitName: string | null;
  setSelectedHabitId: (selectedHabitId: number | null) => void;
  setSelectedHabitName: (selectedHabitName: string | null) => void;
}

function isIncludeName(array: IHabitListDataV2[], name: string) {
  let isIncluded = false;

  for (let i = 0; i < array.length; i++) {
    if (array[i].habitName === name) {
      isIncluded = true;
      break;
    }
  }

  return isIncluded;
}

export const HabitSearchBox = ({
  selectedHabitId,
  selectedHabitName,
  setSelectedHabitId,
  setSelectedHabitName,
}: IHabitSearchBox) => {
  const [searchedCategoryList, setSearchedCategoryList] = useState<
    IHabitListDataV2[]
  >([]);
  const regExp = /^[ㄱ-ㅎ가-힣\s]+$/;

  return (
    <section className="w-full flex flex-col gap-[0.5rem]">
      <label className="custom-semibold-text text-custom-matcha">
        해빗을 검색해주세요
      </label>

      <GuideText text="미리 정해둔 해빗 키워드를 검색하고 하나를 골라주세요" />

      <input
        type="text"
        className="w-full px-[1.5rem] py-[0.7rem] rounded-[3rem] bg-transparent custom-medium-text outline-none border-[0.1rem] border-custom-medium-gray focus:border-custom-matcha"
        onChange={async (event) => {
          if (
            event.target.value.length !== 0 &&
            event.target.value.replaceAll(' ', '').length !== 0
          ) {
            if (regExp.test(event.target.value)) {
              const categoryList = await searchCategoryList(event.target.value);
              setSearchedCategoryList(categoryList.data.habitList);
            }
          } else {
            setSearchedCategoryList([]);
          }
        }}
      ></input>

      <div className="flex gap-[1rem] flex-wrap">
        {selectedHabitId &&
          selectedHabitName &&
          !isIncludeName(searchedCategoryList, selectedHabitName) && (
            <HabitNameButton
              name={selectedHabitName}
              isSelected={true}
              onClick={() => {
                setSelectedHabitId(null);
                setSelectedHabitName(null);
              }}
            />
          )}

        {searchedCategoryList?.map((searchedHabit) => {
          return (
            <HabitNameButton
              key={`habit-${searchedHabit.habitId}`}
              name={searchedHabit.habitName}
              isSelected={selectedHabitId === searchedHabit.habitId}
              onClick={() => {
                if (selectedHabitId === searchedHabit.habitId) {
                  setSelectedHabitId(null);
                  setSelectedHabitName(null);
                } else {
                  setSelectedHabitId(searchedHabit.habitId);
                  setSelectedHabitName(searchedHabit.habitName);
                }
              }}
            />
          );
        })}
      </div>
    </section>
  );
};
