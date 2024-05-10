'use client';

import { InformationCircleIcon } from '@heroicons/react/24/outline';
import { useState } from 'react';

interface IPopOverProps {
  title: string;
  //   position: 'top' | 'bottom' | 'left' | 'right';
  children?: React.ReactNode;
}
export const PopOver = ({
  title,
  // position,
  children,
}: IPopOverProps) => {
  const [isOpen, setIsOpen] = useState(false);
  return (
    <div className="relative">
      <button
        className="flex items-center justify-center w-8 h-8 text-gray-400 rounded-full"
        onClick={() => setIsOpen(!isOpen)}
      >
        <InformationCircleIcon className="w-8 h-8" />
      </button>
      {isOpen && (
        <div className="absolute z-10 w-48 p-2 mt-2 text-sm text-gray-600 bg-white border border-gray-200 rounded-lg shadow-lg">
          {/* <p>{text}</p> */}
          <div className="font-bold">{title}</div>
          {children}
        </div>
      )}
    </div>
  );
};
