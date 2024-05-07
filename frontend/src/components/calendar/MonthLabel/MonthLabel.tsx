'use client';

import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/24/solid';
import { useRouter } from 'next/navigation';

import { getMonth } from '../util';
interface IMonthLabel {
  year: number;
  month: number;
}
export const MonthLabel = ({ year, month }: IMonthLabel) => {
  const router = useRouter();

  const prevMonth = (year: number, month: number): number[] => {
    return month === 1 ? [year - 1, 12] : [year, month - 1];
  };
  const nextMonth = (year: number, month: number): number[] => {
    return month === 12 ? [year + 1, 1] : [year, month + 1];
  };
  const routeMonth = ([year, month]: number[]) => {
    router.push(`/main/${year}/${month}`);
  };
  return (
    <>
      <div className="flex w-full space-x-4 justify-around my-4">
        <ChevronLeftIcon
          onClick={() => routeMonth(prevMonth(year, month))}
          className="w-8 h-8"
        />
        <p className="flex font-bold text-3xl text-center">{month}월</p>
        {getMonth(false) !== month + '' ? (
          <ChevronRightIcon
            onClick={() => routeMonth(nextMonth(year, month))}
            className="w-8 h-8"
          />
        ) : (
          <div className="w-8 h-8" />
        )}
      </div>
    </>
  );
};
