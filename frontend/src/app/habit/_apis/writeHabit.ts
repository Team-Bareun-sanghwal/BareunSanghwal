export async function writeHabit(
  image: File | null,
  HabitTrackerModifyReqDto: {
    habitTrackerId: number;
    content: string | null;
  },
) {
  const habitFormData = new FormData();
  if (image) habitFormData.append('image', image);
  habitFormData.append(
    'HabitTrackerModifyReqDto',
    new Blob([JSON.stringify(HabitTrackerModifyReqDto)], {
      type: 'application/json',
    }),
  );

  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/habits/completion`,
    {
      method: 'PATCH',
      headers: {
        Authorization:
          'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6IjE2Iiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTcxNTAzOTczOCwiZXhwIjoxNzE1OTAzNzM4fQ.KvEeNhUaAmp6clxvQnj2X4tB8GRytsV2xkvWLH6S6uk',
      },
      credentials: 'include',
      body: habitFormData,
    },
  );

  if (!response.ok) throw new Error('해빗을 기록하는데 실패');
  return response.json();
}
