import { cookies } from 'next/headers';
import { HabitWriteFunnel } from '../_components/HabitWriteFunnel';

export default async function Page({
  params,
}: {
  params: { habitTrackerId: number };
}) {
  const cookieStore = cookies();
  const authorization = cookieStore.get('Authorization')?.value;

  return (
    <HabitWriteFunnel
      habitTrackerId={params.habitTrackerId}
      authorization={authorization}
    />
  );
}
