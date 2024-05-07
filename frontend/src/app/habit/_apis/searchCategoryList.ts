export async function searchCategoryList(category: string) {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/habits/search?habitName=${category}`,
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

  if (!response.ok) throw new Error('해빗 카테고리 검색 실패');
  return response.json();
}
