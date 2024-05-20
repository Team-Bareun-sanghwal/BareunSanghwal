import { $Fetch } from '@/apis';

export const getStatisticsData = async () => {
  const result = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/statistic`,
    cache: 'default',
  });
  if ((await result.status) === 200) {
    return result.data;
  }
};
