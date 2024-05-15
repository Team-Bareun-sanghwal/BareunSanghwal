'use client';

import { InformationCircleIcon } from '@heroicons/react/24/outline';
import { useState } from 'react';

interface IPopOverProps {
  title: string;
  description?: string[];
  //   position: 'top' | 'bottom' | 'left' | 'right';
  children?: React.ReactNode;
}
export const PopOver = ({
  title,
  // position,
  description,
  children,
}: IPopOverProps) => {
  const [isOpen, setIsOpen] = useState(false);
  return (
    <div className="relative m-1">
      <button
        className="flex items-center justify-center w-8 h-8 text-gray-400 rounded-full"
        onClick={() => setIsOpen(!isOpen)}
      >
        <InformationCircleIcon className="w-8 h-8" />
      </button>
      {isOpen && (
        <div className="inline-block absolute z-10 w-auto p-2 mt-2 text-sm text-gray-600 bg-white border border-gray-200 rounded-lg shadow-lg">
          {/* <p>{text}</p> */}
          <div className="font-bold">{title}</div>
          {description?.map((desc, idx) => (
            <span className="flex whitespace-nowrap" key={idx}>
              {desc}
            </span>
          ))}
          {children}
        </div>
      )}
    </div>
  );
};
