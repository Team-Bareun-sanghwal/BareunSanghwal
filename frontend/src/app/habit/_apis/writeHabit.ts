export async function writeHabit(
  image: string,
  habitCompletionReqDto: {
    habitTrackerId: number;
    content: string;
  },
) {
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/habits/completion`,
    {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization:
          'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtZW1iZXJJZCI6IjEiLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzE0MTA0Njg3LCJleHAiOjE3MTMzMzMzOTEwODd9.fiwjrUdcc14-eLMUuhYtYQxLEP9eEynCnUMyBTdjXBI',
      },
      credentials: 'include',
      body: JSON.stringify({ image, habitCompletionReqDto }),
    },
  );

  if (!response.ok) throw new Error('해빗을 기록하는데 실패');
  return response.json();
}
