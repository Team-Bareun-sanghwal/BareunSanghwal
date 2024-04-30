'use client';

import { useEffect, useState } from 'react';
import { ProgressBlock } from '../ProgressBlock/ProgressBlock';

interface IPropType {
  pageIdx: number;
  increasePageIdx: () => void;
}

export const ProgressBar = ({ pageIdx, increasePageIdx }: IPropType) => {
  return (
    <div className="w-full h-[0.5rem] flex justify-around items-center">
      {[...Array(9)].map((_, index) => (
        <ProgressBlock
          key={index}
          nowIdx={index}
          pageIdx={pageIdx}
          increasePageIdx={increasePageIdx}
        />
      ))}
    </div>
  );
};
