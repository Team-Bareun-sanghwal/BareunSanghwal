import { $Fetch } from '@/apis';

export async function getActivatedHabitList() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/active`,
    cache: 'default',
  });

  if (response.status !== 200)
    throw new Error('활성화된 해빗 목록을 불러오는데 실패');
  return response;
}
