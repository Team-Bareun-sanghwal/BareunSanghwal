import { LoginButton } from '@/components';

export default function Page() {
  return (
    <div className="bg-custom-matcha w-full h-screen">
      <div className="flex flex-col items-center gap-[1rem]">
        <LoginButton platform="kakao" />
        <LoginButton platform="google" />
      </div>
    </div>
  );
}
