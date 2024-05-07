'use client'; // Error components must be Client Components

import { useEffect } from 'react';

export default function Error({
  error,
  reset,
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) {
  useEffect(() => {
    // Log the error to an error reporting service
    console.error(error);
  }, [error]);

  return (
    <div className="flex flex-col items-center">
      <h2 className="flex text-2xl">스트릭을 불러오는데 실패했어요..</h2>
      <button className="flex text-2xl" onClick={() => reset()}></button>
    </div>
  );
}
