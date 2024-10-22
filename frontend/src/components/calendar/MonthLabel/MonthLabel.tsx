import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/24/solid';
import { getMonth,getYear } from '../util';
interface IMonthLabel {
  year: number;
  month: number;
  setMonth: React.Dispatch<React.SetStateAction<number>>;
  setYear: React.Dispatch<React.SetStateAction<number>>;
}
export const MonthLabel = ({ year, month, setMonth, setYear }: IMonthLabel) => {
  const prevMonth = (year: number, month: number) => {
    setMonth(month === 1 ? 12 : month - 1);
    if (month === 1) setYear(year - 1);
  };
  const nextMonth = (year: number, month: number) => {
    setMonth(month === 12 ? 1 : month + 1);
    if (month === 12) setYear(year + 1);
  };
  return (
    <>
      <div className="flex w-full space-x-4 justify-around my-4 items-center">
        <ChevronLeftIcon
          onClick={() => prevMonth(year, month)}
          className="w-8 h-8"
        />
        <p className="flex font-bold text-3xl text-center items center">
          {month}월
        </p>
        {!(parseInt(getMonth(false))=== month && parseInt(getYear()) === year)? (
          <ChevronRightIcon
            onClick={() => nextMonth(year, month)}
            className="w-8 h-8"
          />
        ) : (
          <div className="w-8 h-8" />
        )}
      </div>
    </>
  );
};
