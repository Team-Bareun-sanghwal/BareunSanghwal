'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { ProgressBar } from '../ProgressBar/ProgressBar';
import { RecapHeader } from '../RecapHeader/RecapHeader';
import { RecapTitle } from '../RecapTitle/RecapTitle';
import { RecapHabitList } from '../RecapHabitList/RecapHabitList';
import { Trophy } from '../Trophy/Trophy';
import { RecapBarChart } from '../RecapBarChart/RecapBarChart';
import { RecapPieChart } from '../RecapPieChart/RecapPieChart';
import { RecapStars } from '../RecapStars/RecapStars';
import { RecapKeyword } from '../RecapKeyword/RecapKeyword';
import { RecapFinish } from '../RecapFinish/RecapFinish';
import { RecapImage } from '../RecapImage/RecapImage';
import { RecapAnimal } from '../RecapAnimal/RecapAnimal';

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

  const router = useRouter();

  // 페이지 별 타이틀, 페이지 배열로 만들 것
  const recapContentArr = [
    {
      title: '이번 달에 실천한 해빗이에요',
      content: (
        <RecapHabitList key={0} rateByMemberHabitList={rateByMemberHabitList} />
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
      content: <RecapAnimal key={4} mostSubmitTime={mostSubmitTime} />,
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
      content: <RecapImage key={7} image={image} />,
    },
    {
      title: `${month}월도 너무 잘했어요!`,
      content: <RecapFinish key={8} month={month} />,
    },
    {
      title: `${month}월도 너무 잘했어요!`,
      content: <RecapFinish key={9} month={month} />,
    },
  ];

  const [pageIdx, setPageIdx] = useState(4);

  const increasePageIdx = () => {
    if (pageIdx === 8) {
      router.back();
    }
    setPageIdx((prev) => prev + 1);
  };

  return (
    <div className="bg-custom-black w-full h-screen flex flex-col p-[1rem]">
      <ProgressBar pageIdx={pageIdx} increasePageIdx={increasePageIdx} />
      <RecapHeader memberName={memberName} year={year} month={month} />
      {pageIdx === 8 ? null : (
        <RecapTitle title={recapContentArr[pageIdx].title} />
      )}

      {recapContentArr[pageIdx].content}
    </div>
  );
};
