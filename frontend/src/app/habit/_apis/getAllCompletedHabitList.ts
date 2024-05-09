import { $Fetch } from '@/apis';

export async function getCompletedHabitList() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/non-active`,
    cache: 'no-store',
  });

  if (response.status !== 200)
    throw new Error('완료된 해빗 목록을 불러오는데 실패');
  return response;
}
