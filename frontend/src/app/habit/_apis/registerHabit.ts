export async function registerHabit(
  habitId: number,
  alias: string,
  icon: string,
  dayOfWeek: number[] | null,
  period: number | null,
) {
  const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/habits`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization:
        'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6IjEiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0MTA0Njg3LCJleHAiOjE3MTMzMzMzOTEwODd9.fiwjrUdcc14-eLMUuhYtYQxLEP9eEynCnUMyBTdjXBI',
    },
    credentials: 'include',
    body: JSON.stringify({ habitId, alias, icon, dayOfWeek, period }),
  });

  if (!response.ok) throw new Error('해빗을 등록하는데 실패');
  return response.json();
}
