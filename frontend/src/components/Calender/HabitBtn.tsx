import { IMemberHabit } from './mockserver';

const HabitBtn = ({ memberHabitId, alias, icon }: IMemberHabit) => {
  return (
    <>
      <div className="flex flex-col items-center justify-center ">
        <button className="bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full">
          {icon}
        </button>
        <p className="text-xs text-center w-16 truncate ...">{alias}</p>
      </div>
    </>
  );
};
export default HabitBtn;
