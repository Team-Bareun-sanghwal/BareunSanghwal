'use client';

import Image from 'next/image';

interface IHabitButtonProps {
  isDone: boolean;
  label: string;
  iconSrc: string;
}

export const HabitButton = ({ isDone, label, iconSrc }: IHabitButtonProps) => {
  return (
    <nav className="flex flex-col gap-[1rem] items-center">
      <div
        className={`${isDone ? 'bg-gradient-to-r from-custom-pink to-custom-sky' : 'bg-custom-medium-gray'} w-[7rem] h-[7rem] rounded-full flex justify-center items-center`}
      >
        <button className="w-[6rem] h-[6rem] rounded-full bg-custom-light-gray flex items-center justify-center ring-2 ring-custom-white">
          <Image
            src={iconSrc}
            width={35}
            height={35}
            alt="해빗 아이콘"
            className="bg-contain"
          />
        </button>
      </div>
      <label className="text-[1rem] font-bold">{label}</label>
    </nav>
  );
};
