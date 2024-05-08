import { $Fetch } from '@/apis';

export async function getUserAmountByDay() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/day`,
    cache: 'no-store',
  });

  if (response.status !== 200)
    throw new Error('요일별로 얼마나 많은 사람들이 참여하는지 불러오는데 실패');
  return response;
}
