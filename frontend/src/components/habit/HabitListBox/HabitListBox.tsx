'use client';

import {
  ChevronRightIcon,
  ArrowPathIcon,
  XMarkIcon,
} from '@heroicons/react/24/solid';
import { useRouter } from 'next/navigation';

interface IHabitListBoxProps {
  mode: 'GOING' | 'UPDATE' | 'COMPLETED' | 'REGISTER';
  name: string | null;
  alias: string | null;
  iconSrc: string;
  dayList?: string[];
  currentStreak?: number;
  habitTrackerId?: number;
  createdAt: Date;
  completedAt?: Date;
  onDeleteClick?: () => void;
  onCompleteClick?: () => void;
}

const mapNumberToString = (num: number) => {
  switch (num) {
    case 0:
      return [0, 1, 2, 3, 5, 6, 8, 9, 11, 12, 13, 14];
    case 1:
      return [0, 1, 4, 7, 10, 12, 13, 14];
    case 2:
      return [0, 1, 2, 5, 6, 7, 8, 9, 12, 13, 14];
    case 3:
      return [0, 1, 2, 5, 6, 7, 8, 11, 12, 13, 14];
    case 4:
      return [0, 2, 3, 5, 6, 7, 8, 11, 14];
    case 5:
      return [0, 1, 2, 3, 6, 7, 8, 11, 12, 13, 14];
    case 6:
      return [0, 1, 2, 3, 6, 7, 8, 9, 11, 12, 13, 14];
    case 7:
      return [0, 1, 2, 3, 5, 6, 8, 11, 14];
    case 8:
      return [0, 1, 2, 3, 5, 6, 7, 8, 9, 11, 12, 13, 14];
    case 9:
      return [0, 1, 2, 3, 5, 6, 7, 8, 11, 14];
  }

  return [0, 1, 2, 3, 5, 6, 8, 9, 11, 12, 13, 14];
};

export const HabitListBox = ({
  mode,
  name,
  alias,
  iconSrc,
  dayList,
  currentStreak,
  habitTrackerId,
  createdAt,
  completedAt,
  onDeleteClick,
  onCompleteClick,
}: IHabitListBoxProps) => {
  const router = useRouter();

  return (
    <section className="w-full rounded-[1rem] p-[1rem] flex flex-col gap-[1rem] bg-custom-light-gray">
      <div className="flex items-center justify-between gap-[1rem]">
        <div className="flex gap-[0.5rem] items-center flex-shrink-0">
          <button
            className={`${mode !== 'COMPLETED' ? 'bg-gradient-to-r from-custom-pink to-custom-sky' : 'bg-custom-white'} w-[6rem] h-[6rem] rounded-full flex items-center justify-center`}
          >
            <span className="text-[3rem]">{iconSrc}</span>
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

        {mode !== 'REGISTER' && currentStreak !== undefined ? (
          <div className="flex gap-[0.7rem]">
            {Math.floor(currentStreak / 100) !== 0 && (
              <div className="grid grid-cols-3 gap-y-[0.1rem] gap-x-[0.2rem]">
                {Array.from({ length: 15 }, () => 0).map((_, index) => {
                  return (
                    <div
                      key={`box1-${index}`}
                      className={`${mapNumberToString(Math.floor(currentStreak / 100)).includes(index) ? 'bg-custom-green' : 'bg-custom-white opacity-60'} w-[0.7rem] h-[0.8rem] rounded-sm`}
                    ></div>
                  );
                })}
              </div>
            )}
            <div className="grid grid-cols-3 gap-y-[0.1rem] gap-x-[0.2rem]">
              {Math.floor((currentStreak % 100) / 10) !== 0 &&
                Array.from({ length: 15 }, () => 0).map((_, index) => {
                  return (
                    <div
                      key={`box2-${index}`}
                      className={`${mapNumberToString(Math.floor((currentStreak % 100) / 10)).includes(index) ? 'bg-custom-green' : 'bg-custom-white opacity-60'} w-[0.7rem] h-[0.8rem] rounded-sm`}
                    ></div>
                  );
                })}
            </div>
            <div className="grid grid-cols-3 gap-y-[0.1rem] gap-x-[0.2rem]">
              {Array.from({ length: 15 }, () => 0).map((_, index) => {
                return (
                  <div
                    key={`box3-${index}`}
                    className={`${mapNumberToString(Math.floor(currentStreak % 10)).includes(index) ? 'bg-custom-green' : 'bg-custom-white opacity-60'} w-[0.7rem] h-[0.8rem] rounded-sm`}
                  ></div>
                );
              })}
            </div>
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
            <div
              className="flex items-center"
              onClick={() => {
                router.push(`/habit/write/${habitTrackerId}`);
              }}
            >
              <span className="text-custom-dark-gray">기록하러 가기</span>
              <ChevronRightIcon className="w-[1.8rem] h-[1.8rem] text-custom-dark-gray" />
            </div>
          ) : (
            <ul className="flex divide-x-[0.1rem] divide-custom-medium-gray">
              <li
                className="flex items-center pr-[1rem]"
                onClick={onCompleteClick}
              >
                <ArrowPathIcon className="w-[1.8rem] h-[1.8rem] mr-[0.3rem]" />
                <span>종료하기</span>
              </li>
              <li
                className="flex items-center text-custom-error pl-[1rem]"
                onClick={onDeleteClick}
              >
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
