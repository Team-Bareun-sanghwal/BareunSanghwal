import BlockStack from '@/components/loading/BlockStack/BlockStack';
import { LoginButton } from '@/components';

export default function Page() {
  return (
    <div className="bg-custom-matcha w-full h-dvh py-[20%] flex flex-col justify-between">
      <div className="h-full flex items-center">
        <div className="flex items-center w-full gap-[2rem]">
          <BlockStack />
          <div className="w-[12rem] flex flex-col justify-center gap-[1rem]">
            <div className="text-white w-full custom-medium-text logo-desc">
              <p className="text-center">오늘도, 쌓자!</p>
            </div>
            <div className="w-full logo-bareun-life text-center">
              <div className="text-white custom-logo-text">바른</div>
              <div className="text-white custom-logo-text">생활</div>
            </div>
          </div>
        </div>
      </div>
      <div className="absolute bottom-[10%] w-full flex flex-col self-center items-center gap-[2rem] loginButtons">
        <LoginButton platform="kakao" />
        <LoginButton platform="google" />
      </div>
    </div>
  );
}
