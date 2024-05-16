'use server';

import { revalidatePath } from 'next/cache';
import { writeHabitFetch } from './writeHabitFetch';

export async function writeHabit(
  image: File | null,
  HabitTrackerModifyReqDto: {
    habitTrackerId: number;
    content: string | null;
  },
  authorization?: string,
) {
  const response = await writeHabitFetch(
    image,
    HabitTrackerModifyReqDto,
    authorization,
  );
  revalidatePath('/habit/register');

  return response;
}
