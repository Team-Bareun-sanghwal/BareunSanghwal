import { $Fetch } from '@/apis';

export const getNotificationList = async () => {
  const result = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/notifications`,
    cache: 'default',
  });
  if ((await result.status) === 200) {
    return result.data;
  }
};
