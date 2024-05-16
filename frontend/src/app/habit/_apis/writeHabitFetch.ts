export async function writeHabitFetch(
  image: File | null,
  HabitTrackerModifyReqDto: {
    habitTrackerId: number;
    content: string | null;
  },
  authorization?: string,
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
        Authorization: `${authorization}`,
      },
      credentials: 'include',
      body: habitFormData,
    },
  );

  // if (!response.ok) throw new Error('해빗을 기록하는데 실패');

  return response.json();
}
