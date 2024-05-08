import { HabitWriteFunnel } from '../_components/HabitWriteFunnel';

export default async function Page({
  params,
}: {
  params: { habitTrackerId: number };
}) {
  return <HabitWriteFunnel habitTrackerId={params.habitTrackerId} />;
}
