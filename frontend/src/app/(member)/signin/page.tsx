import { SignInForm } from '@/components/member/SignInForm/SignInForm';

export default function Page() {
  return (
    <div className="h-screen">
      <div className="bg-custom-white px-[1rem] pt-[1rem] flex flex-col justify-between h-full">
        <div className="custom-bold-text text-custom-black h-[6rem] mb-[5rem]">
          <p className="text-custom-matcha">반가워요!</p>
          <p>우리 딱 네 가지만 정하고 시작할까요?</p>
        </div>
        <div className="h-full self-center">
          <SignInForm />
        </div>
      </div>
    </div>
  );
}
