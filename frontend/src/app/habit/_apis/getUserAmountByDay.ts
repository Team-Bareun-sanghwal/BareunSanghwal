export async function getUserAmountByDay() {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/habits/day`,
    {
      headers: {
        'Content-Type': 'application/json',
        Authorization:
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6IjEiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0MTA0Njg3LCJleHAiOjE3MTMzMzMzOTEwODd9.fiwjrUdcc14-eLMUuhYtYQxLEP9eEynCnUMyBTdjXBI',
      },
      credentials: 'include',
      cache: 'no-store',
    },
  );

  if (!response.ok)
    throw new Error('요일별로 얼마나 많은 사람들이 참여하는지 불러오는데 실패');
  return response.json();
}
