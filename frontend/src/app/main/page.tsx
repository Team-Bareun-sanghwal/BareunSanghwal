'use client';
// pages/main/page.tsx
import { useEffect } from 'react';
import { useRouter } from 'next/navigation';

const Page = () => {
  const router = useRouter();

  useEffect(() => {
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth() + 1;
    router.push(`/main/${year}/${month}`);
  }, [router]);

  return null;
};

export default Page;
