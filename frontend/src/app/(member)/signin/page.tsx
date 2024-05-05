import { Button } from '@/components';
import { SignInForm } from '@/components/member/SignInForm/SignInForm';

export default function Page() {
  return (
    <div className="p-[1rem] flex flex-col h-screen">
      <div className="custom-bold-text text-custom-black h-[6rem] mb-[5rem]">
        <p className="text-custom-matcha">반가워요!</p>
        <p>우리 딱 네 가지만 정하고 시작할까요?</p>
      </div>
      <div className="h-full">
        <SignInForm />
      </div>
    </div>
  );
}
