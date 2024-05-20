import { Calender } from '@/components';
import { $Fetch } from '@/apis';

const Page = async () => {
  const colorData = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/streak/color`,
    cache: 'default',
  });
  const { streakName } = colorData.data;
  return (
    <>
      <Calender themeColor={streakName} />
    </>
  );
};
export default Page;
