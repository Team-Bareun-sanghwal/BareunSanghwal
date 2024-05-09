'use client';
import { $Fetch } from '@/apis';

export const PermissionButton = () => {
  return (
    <button
      onClick={() => {
        $Fetch({
          method: 'POST',
          url: `${process.env.NEXT_PUBLIC_BASE_URL}/notifications`,
          cache: 'default',
          data: {
            notificationToken: '2',
          },
        });
      }}
    >
      알림 받기
    </button>
  );
};
