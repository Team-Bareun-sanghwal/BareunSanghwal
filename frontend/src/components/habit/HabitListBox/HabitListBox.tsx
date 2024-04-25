'use client';

import Image from 'next/image';
import {
  ChevronRightIcon,
  ArrowPathIcon,
  XMarkIcon,
} from '@heroicons/react/24/solid';

interface IHabitListBoxProps {
  mode: 'GOING' | 'UPDATE' | 'COMPLETED' | 'REGISTER';
  name: string;
  alias: string;
  iconSrc: string;
  dayList?: string[];
  createdAt: Date;
  completedAt?: Date;
}

export const HabitListBox = ({
  mode,
  name,
  alias,
  iconSrc,
  dayList,
  createdAt,
  completedAt,
}: IHabitListBoxProps) => {
  return (
    <section className="w-full rounded-[1rem] p-[1rem] flex flex-col gap-[1rem] bg-custom-light-gray">
      <div className="flex items-center justify-between gap-[1rem]">
        <div className="flex gap-[0.5rem] items-center flex-shrink-0">
          <button
            className={`${mode !== 'COMPLETED' ? 'bg-gradient-to-r from-custom-pink to-custom-sky' : 'bg-custom-white'} w-[6rem] h-[6rem] rounded-full flex items-center justify-center`}
          >
            <Image
              src={iconSrc}
              width={35}
              height={35}
              alt="해빗 아이콘"
              className="bg-contain"
            />
          </button>

          <div className="flex flex-col gap-[0.2rem]">
            <span className="text-[1.3rem] font-semibold">{alias}</span>
            <span className="block w-fit px-[0.8rem] py-[0.1rem] text-[1rem] font-medium rounded-[1rem] bg-custom-medium-gray">
              {name}
            </span>
            <span className="text-custom-dark-gray text-[0.8rem] font-light">
              {mode === 'REGISTER' ? (
                <></>
              ) : mode === 'COMPLETED' ? (
                `${createdAt.getFullYear()}.${createdAt.getMonth() + 1}.${createdAt.getDate()}부터 ${completedAt?.getFullYear()}.${completedAt && completedAt?.getMonth() + 1}.${completedAt?.getDate()}까지`
              ) : (
                `${createdAt.getFullYear()}.${createdAt.getMonth() + 1}.${createdAt.getDate()}부터 지금까지`
              )}
            </span>
          </div>
        </div>

        {mode !== 'REGISTER' ? (
          <div className="grid grid-cols-11 gap-y-[0.1rem] gap-x-[0.2rem]">
            {Array.from({ length: 55 }, () => 0).map((_, index) => {
              const selectedPixelIndexes = [
                0, 1, 2, 13, 24, 23, 22, 33, 44, 45, 46, 4, 5, 6, 17, 28, 27,
                26, 39, 50, 49, 48, 8, 9, 20, 31, 42, 53, 52, 54,
              ];

              return (
                <div
                  key={`box-${index}`}
                  className={`${selectedPixelIndexes.includes(index) ? 'bg-custom-green' : 'bg-custom-white opacity-60'} w-[0.7rem] h-[0.8rem] rounded-sm`}
                ></div>
              );
            })}
          </div>
        ) : (
          <div className="flex gap-[0.2rem] items-center justify-end flex-wrap">
            {dayList?.map((day, index) => {
              return (
                <div
                  key={`day-${index}`}
                  className="w-[2rem] h-[2rem] flex items-center justify-center rounded-full bg-custom-matcha text-custom-white custom-light-text"
                >
                  {day}
                </div>
              );
            })}
          </div>
        )}
      </div>

      {mode !== 'COMPLETED' && mode !== 'REGISTER' && (
        <div className="w-full h-[0.1rem] rounded-lg bg-custom-medium-gray"></div>
      )}

      {mode !== 'COMPLETED' && mode !== 'REGISTER' && (
        <nav className="mx-auto flex items-center cursor-pointer">
          {mode === 'GOING' ? (
            <>
              <span className="text-custom-dark-gray">기록하러 가기</span>
              <ChevronRightIcon className="w-[1.8rem] h-[1.8rem] text-custom-dark-gray" />
            </>
          ) : (
            <ul className="flex divide-x-[0.1rem] divide-custom-medium-gray">
              <li className="flex items-center pr-[1rem]">
                <ArrowPathIcon className="w-[1.8rem] h-[1.8rem] mr-[0.3rem]" />
                <span>종료하기</span>
              </li>
              <li className="flex items-center text-custom-error pl-[1rem]">
                <XMarkIcon className="w-[1.8rem] h-[1.8rem]" />
                <span>삭제하기</span>
              </li>
            </ul>
          )}
        </nav>
      )}
    </section>
  );
};
