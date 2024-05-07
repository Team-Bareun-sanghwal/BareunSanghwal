'use client';
import { IMemberHabit } from '@/app/mock';
import { useRouter } from 'next/navigation';
export const HabitBtn = ({
  memberHabitId,
  alias,
  icon,
  habitId,
}: IMemberHabit) => {
  const router = useRouter();

  const onClickRouter = () => {
    if (memberHabitId == habitId) {
      router.push(`/main/2024/5`);
    } else {
      router.push(`/main/2024/5/${memberHabitId}`);
    }
  };
  return (
    <>
      <div className="flex flex-col items-center justify-center overflow-hidden">
        <button
          onClick={onClickRouter}
          className={
            memberHabitId == habitId
              ? 'bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full border-2 border-custom-dark-gray'
              : 'bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full'
          }
        >
          {icon}
        </button>
        <p className="text-xs text-center w-16 truncate ...">{alias}</p>
      </div>
    </>
  );
};
