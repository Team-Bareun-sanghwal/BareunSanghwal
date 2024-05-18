import { $Fetch } from '@/apis';
import Point from '../Point/Point';
export const MyPoint = async () => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/point`,
    cache: 'default',
  });
  return (
    <>
      <Point point={response.data.point} />
    </>
  );
};
