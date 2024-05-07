export async function getPopularCategoryList() {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/habits/rank`,
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
    throw new Error('최근 사람들이 등록하는 습관 10가지를 불러오는데 실패');
  return response.json();
}
