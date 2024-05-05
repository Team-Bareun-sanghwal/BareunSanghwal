'use client';

import { useRouter } from 'next/navigation';
import {
  FaceFrownIcon,
  ArrowRightStartOnRectangleIcon,
} from '@heroicons/react/24/outline';

interface IPropType {
  type: string;
}

export const BorderlessButton = ({ type }: IPropType) => {
  const router = useRouter();

  const handleOnClick = () => {
    if (type === 'leave') {
      console.log('leave');
      // api 통신 추가
      // `${process.env.NEXT_PUBLIC_BASE_URL}/members`
    } else {
      console.log('logout');
      // api 통신 추가
      // `${process.env.NEXT_PUBLIC_BASE_URL}/members/logout`
    }
  };

  return (
    <button
      type="button"
      className="w-[7rem] flex items-center"
      onClick={handleOnClick}
    >
      {type === 'leave' ? (
        <>
          <FaceFrownIcon className="w-[1.8rem] h-[1.8rem] text-custom-medium-gray mr-2" />
          <p className="text-custom-medium-gray">회원 탈퇴</p>
        </>
      ) : (
        <>
          <ArrowRightStartOnRectangleIcon className="w-[1.8rem] h-[1.8rem] text-custom-medium-gray mr-2" />
          <p className="text-custom-medium-gray">로그아웃</p>
        </>
      )}
    </button>
  );
};
