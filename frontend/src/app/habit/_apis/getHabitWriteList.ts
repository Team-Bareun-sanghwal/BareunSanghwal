import { $Fetch } from '@/apis';

export async function getHabitWriteList(memberHabitId: number) {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/${memberHabitId}/tracker`,
    cache: 'no-store',
  });

  if (response.status !== 200)
    throw new Error('해빗 기록 목록을 불러오는데 실패');
  return response;
}
