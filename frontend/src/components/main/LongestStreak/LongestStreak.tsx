import { $Fetch } from '@/apis';

export const LongestStreak = async () => {
  const { data } = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/streak`,
    cache: 'no-cache',
  });
  const currentStreak = data.currentStreak;
  return (
    <>
      <div className="flex flex-col w-4/12 bg-custom-sky-pastel justify-center rounded-xl max-w-48 min-h-20">
        {currentStreak !== 0 ? (
          <>
            <p className="text-center text-lg ">오늘로</p>
            <p className="text-center text-2xl font-semibold">
              {currentStreak}일째
            </p>
          </>
        ) : (
          <>
            <p className="text-center text-base font-semibold">
              연속된 스트릭이 없어요..
            </p>
            <p className="text-center text-sm ">해빗을 꾸준히 달성해봐요! </p>
          </>
        )}
      </div>
    </>
  );
};
