'use client';

import { useEffect } from 'react';

interface IPropType {
  nowIdx: number;
  pageIdx: number;
  increasePageIdx: () => void;
}

export const ProgressBlock = ({
  nowIdx,
  pageIdx,
  increasePageIdx,
}: IPropType) => {
  const bgColor = nowIdx <= pageIdx ? 'bg-white' : 'bg-custom-dark-gray';

  useEffect(() => {
    let pageIdxTimer: NodeJS.Timeout;

    if (nowIdx === pageIdx) {
      if (pageIdx < 9) {
        pageIdxTimer = setInterval(() => {
          increasePageIdx();
        }, 5000);
      }

      return () => {
        clearInterval(pageIdxTimer);
      };
    }
  }, [increasePageIdx, nowIdx, pageIdx]);

  return (
    <div className={`bg-custom-dark-gray w-[3.3rem] h-[0.5rem] rounded-full`}>
      {nowIdx === pageIdx ? (
        <div className="progressBlock rounded-full" />
      ) : (
        <div className={`${bgColor} w-[3.3rem] h-[0.5rem] rounded-full`} />
      )}
    </div>
  );
};
