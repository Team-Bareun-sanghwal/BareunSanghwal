import { $Fetch } from '@/apis';

export const MyStreakRecovery = async () => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/recovery-count`,
    cache: 'no-cache',
  });
  console.log(response);
  const { total, free } = response.data;

  return (
    <>
      <div className="w-full text-end pr-4 text-md">
        <span className=" text-end">사용 가능한 리커버리 : </span>
        <span className="text-custom-matcha font-bold">{total}</span>
      </div>
      <div className="w-full text-end pr-4 text-md">
        <span className=" text-end">무료 리커버리 : </span>
        <span className="text-custom-matcha font-bold">{free}</span>
      </div>
    </>
  );
};
