'use client';

import { useRouter } from 'next/navigation';

export const RouteHome = () => {
  const router = useRouter();

  const exit = () => {
    router.back();
  };

  return (
    <>
      <button onClick={exit} className="absolute z-10 m-4 text-lg">
        {'<'} 뒤로가기
      </button>
    </>
  );
};
