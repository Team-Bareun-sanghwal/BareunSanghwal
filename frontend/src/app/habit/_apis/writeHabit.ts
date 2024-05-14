'use server';

import { revalidatePath } from 'next/cache';

export async function writeHabit(
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
        // Authorization: `${process.env.NEXT_PUBLIC_ACCESS_TOKEN}`,
      },
      credentials: 'include',
      body: habitFormData,
    },
  );

  // if (!response.ok) throw new Error('해빗을 기록하는데 실패');

  revalidatePath('/habit/list/[memberHabitId]', 'page');

  return response.json();
}
