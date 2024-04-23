import { IMemberHabit } from '@/app/mock';
import { btnSize, BtnSize, HabbitBtnProps } from '../util';
const HabitBtn = ({ memberHabitId, alias, icon, size }: HabbitBtnProps) => {
  const buttonSize = size ? btnSize[size] : btnSize.default;
  return (
    <>
      <div className="flex flex-col items-center justify-center ">
        <button
          className={`bg-custom-sky-pastel text-2xl w-${buttonSize} h-${buttonSize} rounded-full`}
        >
          {icon}
        </button>
        <p className={`text-xs text-center w-${buttonSize} truncate ...`}>
          {alias}
        </p>
      </div>
    </>
  );
};
export default HabitBtn;
