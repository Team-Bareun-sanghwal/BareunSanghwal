'use client';

import { useState } from 'react';
import { IHabitListDataV2 } from '@/app/habit/_types';
import { searchCategoryList } from '@/app/habit/_apis/searchCategoryList';

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
  setSelectedHabitId: (selectedHabitId: number | null) => void;
}

export const HabitSearchBox = ({
  selectedHabitId,
  setSelectedHabitId,
}: IHabitSearchBox) => {
  const [searchedCategoryList, setSearchedCategoryList] = useState<
    IHabitListDataV2[]
  >([]);
  const regExp = /^[ㄱ-ㅎ가-힣\s]+$/;

  return (
    <section className="w-full flex flex-col gap-[1.5rem]">
      <label className="custom-semibold-text text-custom-black">
        해빗을 검색해주세요
      </label>

      <input
        type="text"
        className="w-full px-[1.5rem] py-[0.7rem] rounded-[3rem] bg-transparent custom-medium-text outline-none border-[0.1rem] border-custom-medium-gray focus:border-custom-matcha"
        onChange={async (event) => {
          // 아래 경우에 검색 API 사용
          if (
            event.target.value.length !== 0 &&
            event.target.value.replaceAll(' ', '').length !== 0
          ) {
            if (regExp.test(event.target.value)) {
              setSelectedHabitId(null);
              const categoryList = await searchCategoryList(event.target.value);
              if (categoryList.data.habitList.length !== 0) {
                setSearchedCategoryList(categoryList.data.habitList);
              }
            }
          }
        }}
      ></input>

      <div className="flex gap-[1rem] flex-wrap -mt-[0.5rem]">
        {searchedCategoryList?.map((searchedHabit) => {
          return (
            <HabitNameButton
              key={`habit-${searchedHabit.habitId}`}
              name={searchedHabit.habitName}
              isSelected={selectedHabitId === searchedHabit.habitId}
              onClick={() => setSelectedHabitId(searchedHabit.habitId)}
            />
          );
        })}
      </div>
    </section>
  );
};
