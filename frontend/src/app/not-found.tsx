import { ErrorPage } from '@/components';

export default function NotFound() {
  return (
    <div className="w-full h-dvh flex justify-center items-center">
      <ErrorPage
        errorTitle="페이지를 찾을 수 없어요"
        errorDescription="잘못된 접근입니다. 메인으로 돌아가주세요."
        buttonText="홈으로 가기"
        nextPage={`/main`}
      />
    </div>
  );
}
