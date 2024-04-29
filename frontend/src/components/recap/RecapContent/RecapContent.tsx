'use client';

import { useState } from 'react';
import { ProgressBar } from '../ProgressBar/ProgressBar';
import { RecapHeader } from '../RecapHeader/RecapHeader';
import { RecapTitle } from '../RecapTitle/RecapTitle';
import { Trophy } from '../Trophy/Trophy';
import { RecapBarChart } from '../RecapBarChart/RecapBarChart';
import { RecapPieChart } from '../RecapPieChart/RecapPieChart';
import { RecapStars } from '../RecapStars/RecapStars';
import { RecapKeyword } from '../RecapKeyword/RecapKeyword';

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
  const recapContentArr = [
    {
      title: '이번 달에 실천한 해빗이에요',
      content: (
        <div key={0} className="text-white">
          이거이거 했넹
        </div>
      ),
    },
    {
      title: '이번 달의 대표 해빗은...',
      content: <Trophy key={1} mainHabit={mostSucceededMemberHabit} />,
    },
    {
      title: '해빗 별 달성률은 이랬네요',
      content: (
        <RecapBarChart
          key={2}
          rateByMemberHabitList={rateByMemberHabitList}
          averageRateByMemberHabit={averageRateByMemberHabit}
        />
      ),
    },
    {
      title: '해빗 카테고리 비율은 이랬어요 ',
      content: (
        <RecapPieChart
          key={3}
          rateByHabitList={rateByHabitList}
          mostSuccessedHabit={mostSucceededHabit}
        />
      ),
    },
    {
      title: '내 습관을 동물로 말하자면...',
      content: (
        <div key={4} className="text-white">
          크앙
        </div>
      ),
    },
    {
      title: '이번 달에 모든 별은 몇 개?',
      content: <RecapStars key={5} collectedStar={collectedStar} />,
    },
    {
      title: '이번 달 나의 키워드는...',
      content: <RecapKeyword key={6} keyword={myKeyWord} />,
    },
    {
      title: '이번 달 나의 대표 사진이에요!',
      content: (
        <div key={7} className="text-white">
          사진사진
        </div>
      ),
    },
    {
      title: `${month}월도 너무 잘했어요!`,
      content: (
        <div key={8} className="text-white">
          잘해써어
        </div>
      ),
    },
    {
      title: `끝나따 !`,
      content: (
        <div key={9} className="text-white">
          끝이야용
        </div>
      ),
    },
  ];

  const [pageIdx, setPageIdx] = useState(0);

  const increasePageIdx = () => {
    setPageIdx((prev) => prev + 1);
  };

  return (
    <div className="bg-custom-black w-full h-screen flex flex-col">
      <ProgressBar pageIdx={pageIdx} increasePageIdx={increasePageIdx} />
      <RecapHeader memberName={memberName} year={year} month={month} />
      <RecapTitle title={recapContentArr[pageIdx].title} />
      {recapContentArr[pageIdx].content}
    </div>
  );
};
