'use client';

import { useRouter } from 'next/navigation';

export const Page = () => {
  const router = useRouter();
  const goToMain = () => {
    router.push('/');
  };
  goToMain();
  console.log('카카오');

  return <div>우엉ㅇ 카카오</div>;
};
