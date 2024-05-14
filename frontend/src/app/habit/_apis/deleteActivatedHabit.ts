'use server';

import { revalidatePath } from 'next/cache';
import { $Fetch } from '@/apis';

export async function deleteActivatedHabit(
  memberHabitId: number,
  isDeleteAll: boolean,
) {
  const response = await $Fetch({
    method: 'POST',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/delete`,
    cache: 'default',
    data: { memberHabitId, isDeleteAll },
  });

  // if (response.status !== 200)
  //   throw new Error('활성화된 해빗을 삭제하는데 실패');

  revalidatePath('/habit', 'page');

  return response;
}
