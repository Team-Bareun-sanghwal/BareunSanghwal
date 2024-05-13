import { $Fetch } from '@/apis';

export const readNotificationList = async () => {
  await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/notifications/read`,
    cache: 'no-store',
  });
};
