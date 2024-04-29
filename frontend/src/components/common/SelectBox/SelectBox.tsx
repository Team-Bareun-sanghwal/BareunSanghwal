'use client';

import { useState } from 'react';

interface SelectBoxProps {
  label?: string;
  options: string[];
}

export const SelectBox = ({ label, options }: SelectBoxProps) => {
  const [selectedValue, setSelectedValue] = useState<string | null>(null);

  return (
    <section className="w-full flex flex-col gap-[1rem]">
      {label && (
        <label className="custom-semibold-text text-custom-black">
          {label}
        </label>
      )}

      <ul className="rounded-[0.8rem] list-none flex border-[0.15rem] border-custom-dark-gray custom-light-text overflow-hidden">
        {options.map((option, index) => {
          return (
            <li
              key={`option-${index}`}
              className={`${index !== options.length - 1 && 'border-r-[0.15rem]'} ${selectedValue === option ? 'bg-custom-dark-gray' : 'bg-none'} ${selectedValue === option ? 'text-custom-white' : 'text-custom-black'} border-custom-dark-gray py-[0.8rem] text-center flex-grow cursor-pointer`}
              onClick={() => setSelectedValue(option)}
            >
              {option}
            </li>
          );
        })}
      </ul>
    </section>
  );
};
