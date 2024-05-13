import { $Fetch } from '@/apis';

export async function registerHabit(
  habitId: number,
  alias: string,
  icon: string,
  dayOfWeek: number[] | null,
  period: number | null,
) {
  const response = await $Fetch({
    method: 'POST',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits`,
    cache: 'default',
    data: { habitId, alias, icon, dayOfWeek, period },
  });

  // if (response.status !== 201) {
  //   throw new Error('해빗을 등록하는데 실패');
  // }

  return response;
}
