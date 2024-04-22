import {
  StarIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
} from '@heroicons/react/20/solid';
interface IMonthLabel {
  year: number;
  month: number;
}
const MonthLabel = ({ year, month }) => {
  return (
    <>
      <div className="flex w-full space-x-4 justify-around my-4">
        <ChevronLeftIcon className="w-8 h-8" />
        <p className="flex font-bold text-3xl text-center">{month}월</p>
        <ChevronRightIcon className="w-8 h-8" />
      </div>
    </>
  );
};
export default MonthLabel;
