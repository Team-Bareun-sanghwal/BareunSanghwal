import { $Fetch } from '@/apis';
import Point from '../Point/Point';
export const MyPoint = async () => {
  const { point } = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/point`,
    cache: 'no-cache',
  });
  return <Point point={point} />;
};
