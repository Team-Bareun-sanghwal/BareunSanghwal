export async function getSimpleActivatedHabitList() {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/habits/active-day`,
    {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `${process.env.NEXT_PUBLIC_ACCESS_TOKEN}`,
      },
      credentials: 'include',
      cache: 'no-store',
    },
  );

  if (!response.ok)
    throw new Error(
      '활성화된 간단 해빗 목록을 불러오는데 실패 - 해빗 등록 시 사용',
    );
  return response.json();
}
