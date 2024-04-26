'use client';

import { XMarkIcon } from '@heroicons/react/24/solid';
import { useRouter } from 'next/navigation';

interface IPropType {
  memberName: string;
  year: number;
  month: number;
}

export const RecapHeader = ({ memberName, year, month }: IPropType) => {
  const router = useRouter();
  const handleOnClick = () => {
    // 추후 리캡 목록 경로로 변경
    router.push('/');
  };

  return (
    <div className="w-full h-fit">
      <div className="flex justify-between items-center py-[1.5rem]">
        <p className="text-white custom-semibold-text">
          {memberName}님의 {year}년 {month}월
        </p>
        <button type="button" onClick={handleOnClick}>
          <XMarkIcon className="fill-white w-[3rem] h-[3rem]" />
        </button>
      </div>
      <hr />
      <br />
    </div>
  );
};
