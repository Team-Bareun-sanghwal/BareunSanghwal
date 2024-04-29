'use client';

import {
  ArrowPathIcon,
  CheckCircleIcon,
  PencilIcon,
} from '@heroicons/react/24/solid';

interface ITinyButtonProps {
  mode: 'MODIFY' | 'SAVE' | 'RECOMMEND';
  label: string;
  onClick?: () => void;
}

export const TinyButton = ({ mode, label, onClick }: ITinyButtonProps) => {
  const buttonColor =
    mode === 'MODIFY' ? 'bg-custom-light-gray' : 'bg-custom-matcha';
  const textColor =
    mode === 'MODIFY' ? 'text-custom-black' : 'text-custom-white';

  return (
    <button
      className={`${buttonColor} ${textColor} w-fit h-[2.5rem] px-[1rem] py-[0.5rem] custom-light-text rounded-[0.8rem] flex items-center gap-[0.5rem]`}
      onClick={onClick}
    >
      {mode === 'MODIFY' ? (
        <PencilIcon className="w-[1.5rem] h-[1.5rem] text-custom-black" />
      ) : mode === 'SAVE' ? (
        <CheckCircleIcon className="w-[1.5rem] h-[1.5rem] text-custom-white" />
      ) : (
        <ArrowPathIcon className="w-[1.5rem] h-[1.5rem] text-custom-white" />
      )}
      {label}
    </button>
  );
};
