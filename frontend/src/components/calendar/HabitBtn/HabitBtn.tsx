'use client';

import { useRouter } from 'next/navigation';

export const HabitBtn = ({
  memberHabitId,
  alias,
  icon,
  habitId,
  shortcut,
}: {
  memberHabitId: number;
  alias: string;
  icon: string;
  habitId?: number;
  shortcut?: boolean;
}) => {
  const router = useRouter();

  const onClickHabit = () => {
    if(shortcut){
      console.log('here shortcut')
    }
    else{
      if (memberHabitId == habitId) {
        router.push(`/main/2024/5`);
      } else {
        router.push(`/main/2024/5/${memberHabitId}`);
      }
    }
  };

  return (
    <>
      <div className="flex flex-col items-center justify-center">
        <button
          onClick={onClickHabit}
          className={
            shortcut? 'bg-custom-sky-pastel text-2xl w-24 h-24 rounded-full border-2'
            : memberHabitId == habitId
              ? 'bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full border-2 border-custom-dark-gray'
              : 'bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full'
          }
        >
          {icon}
        </button>
        <p className={shortcut?
          "text-s mt-1 text-center w-24 truncate ...":
          "text-xs text-center w-16 truncate ..."
        }>{alias}</p>
      </div>
    </>
  );
};
