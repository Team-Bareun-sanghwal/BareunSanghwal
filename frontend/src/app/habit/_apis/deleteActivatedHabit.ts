export async function deleteActivatedHabit(
  memberHabitId: number,
  isDeleteAll: boolean,
) {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/habits/delete`,
    {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization:
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6IjEiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0MTA0Njg3LCJleHAiOjE3MTMzMzMzOTEwODd9.fiwjrUdcc14-eLMUuhYtYQxLEP9eEynCnUMyBTdjXBI',
      },
      credentials: 'include',
      body: JSON.stringify({ memberHabitId, isDeleteAll }),
    },
  );

  if (!response.ok) throw new Error('활성화된 해빗을 삭제하는데 실패');
  return response.json();
}
