import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/24/solid';
interface IMonthLabel {
  year: number;
  month: number;
}
const MonthLabel = ({ year, month }: IMonthLabel) => {
  const y = year;
  return (
    <>
      <div className="flex w-full space-x-4 justify-around my-4">
        <ChevronLeftIcon className="w-6 h-6" />
        <p className="flex font-semibold text-2xl text-center">{month}월</p>
        <ChevronRightIcon className="w-6 h-6" />
      </div>
    </>
  );
};
export default MonthLabel;
