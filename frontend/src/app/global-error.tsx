import { ErrorPage } from '@/components';

export default function GlobalError({
  error,
  reset,
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) {
  return (
    <div className="w-full h-dvh flex justify-center items-center">
      <ErrorPage
        errorTitle="예기치 않은 오류가 발생했어요"
        errorDescription="오류가 발생했어요. 다시 시도해주세요."
        buttonText="메인으로 가기"
        nextPage="/main"
      />
    </div>
  );
}
