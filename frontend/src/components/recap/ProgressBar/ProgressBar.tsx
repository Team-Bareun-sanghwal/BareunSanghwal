'use client';

import { useEffect, useState } from 'react';
import { ProgressBlock } from '../ProgressBlock/ProgressBlock';

interface IPropType {
  pageIdx: number;
  increasePageIdx: () => void;
}

export const ProgressBar = ({ pageIdx, increasePageIdx }: IPropType) => {
  // useEffect(() => {
  //   let timer;
  //   const interval = 1000; // 1초마다 갱신
  //   const step = 100 / duration; // 스토리의 전체 시간에 따른 갱신 간격

  //   // progress 상태를 1초마다 갱신
  //   timer = setInterval(() => {
  //     setProgress((prevProgress) => prevProgress + step);
  //   }, interval);

  //   // 언마운트 시 타이머 해제
  //   return () => clearInterval(timer);
  // }, [duration]);

  // return (
  //   <div className="progress-bar">
  //     <div
  //       className="progress"
  //       style={{ width: `${progress}%`, backgroundColor: 'red' }}
  //     >
  //       {progress}
  //     </div>
  //   </div>
  // );
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
