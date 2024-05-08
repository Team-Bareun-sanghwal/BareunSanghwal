import Image from 'next/image';
import { $Fetch } from '@/apis';
export const DailyPhrase = async () => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/daily-phrase`,
    cache: 'no-cache',
  });
  const { phrase } = response.data;
  return (
    <>
      <div className="flex justify-between overflow-hidden items-center m-4 h-24 bg-custom-light-gray text-custom-dark-gray text-xl rounded-md ">
        <p className="ml-6">{phrase}</p>
        <div className="rotate-[236deg] mb-10">
          <Image src="/images/icon-pencil.png" alt="" width={80} height={80} />
        </div>
      </div>
    </>
  );
};
