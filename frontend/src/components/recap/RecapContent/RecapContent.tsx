'use client';

import { useState } from 'react';
import { ProgressBar } from '../ProgressBar/ProgressBar';
import { RecapHeader } from '../RecapHeader/RecapHeader';
import { RecapTitle } from '../RecapTitle/RecapTitle';
import { Trophy } from '../Trophy/Trophy';

interface IPropType {
  year: number;
  month: number;
  memberName: string;
  mostSucceededHabit: string;
  mostSucceededMemberHabit: string;
  averageRateByMemberHabit: number;
  rateByMemberHabitList: {
    name: string;
    missCount: number;
    actionCount: number;
    ratio: number;
  }[];
  rateByHabitList: {
    name: string;
    ratio: number;
  }[];
  mostSubmitTime: string;
  collectedStar: number;
  myKeyWord: string;
  image: string;
}

export const RecapContent = ({ data }: { data: IPropType }) => {
  // console.log(data);
  const {
    year,
    month,
    memberName,
    mostSucceededHabit,
    mostSucceededMemberHabit,
    averageRateByMemberHabit,
    rateByMemberHabitList,
    rateByHabitList,
    mostSubmitTime,
    collectedStar,
    myKeyWord,
    image,
  } = data;

  // 페이지 별 타이틀, 페이지 배열로 만들 것

  const [pageIdx, setPageIdx] = useState(0);

  const increasePageIdx = () => {
    setPageIdx((prev) => prev + 1);
  };

  return (
    <div className="bg-custom-black w-full h-screen flex flex-col">
      <ProgressBar pageIdx={pageIdx} increasePageIdx={increasePageIdx} />
      <RecapHeader memberName={memberName} year={year} month={month} />
      <RecapTitle title="이번 달에 가장 많이 실천한 해빗은..." />
      <Trophy mainHabit={mostSucceededMemberHabit} />
    </div>
  );
};
