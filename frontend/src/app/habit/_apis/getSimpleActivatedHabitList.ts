import { $Fetch } from '@/apis';

export async function getSimpleActivatedHabitList() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/active-day`,
    cache: 'default',
  });

  if (response.status !== 200)
    throw new Error(
      '활성화된 간단 해빗 목록을 불러오는데 실패 - 해빗 등록 시 사용',
    );
  return response;
}
