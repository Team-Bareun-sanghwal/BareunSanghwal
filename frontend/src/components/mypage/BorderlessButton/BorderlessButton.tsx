'use client';

import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { useOverlay } from '@/hooks/use-overlay';
import { useRouter } from 'next/navigation';
import {
  FaceFrownIcon,
  ArrowRightStartOnRectangleIcon,
} from '@heroicons/react/24/outline';
import { deleteMemberInfo } from '@/app/(member)/mypage/_apis/deleteMemberInfo';
import { $Fetch } from '@/apis';

interface IPropType {
  type: string;
}

export default function BorderlessButton({ type }: IPropType) {
  const overlay = useOverlay();
  const router = useRouter();

  const handleLeave = async () => {
    const result = await deleteMemberInfo();
    if ((await result) === 200) {
      router.push('/');
    }
  };

  const handleOverlay = () => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description="바른생활을 떠나시면 지금까지의 해빗 기록이 전부 사라지고 복구할 수 없어요. 그래도 떠나시겠어요? "
        mode="NEGATIVE"
        onClose={close}
        onConfirm={() => {
          handleLeave();
          close();
        }}
        open={isOpen}
        title="바른생활을 떠나시겠어요?"
      />
    ));
  };

  const handleOnClick = async () => {
    if (type === 'leave') {
      handleOverlay();
    } else {
      await $Fetch({
        method: 'POST',
        url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/logout`,
        cache: 'default',
      });
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
}
