import { NavBar } from '@/components';
import { BorderlessButton } from '@/components/mypage/BorderlessButton/BorderlessButton';
import { InfoModifyList } from '@/components/mypage/InfoModifyList/InfoModifyList';

export default function Page() {
  return (
    <div className="h-screen">
      <div className="bg-custom-white px-[1rem] pt-[1rem] pb-[11rem] flex flex-col justify-between min-h-full">
        <div>
          <p className="text-custom-black custom-bold-text mb-[4rem]">
            내 정보
          </p>
          <InfoModifyList
            userData={{
              nickname: '잉잉',
              birthDate: '1999-01-07',
              gender: 'F',
              job: 'STUDENT',
            }}
          />
        </div>
        <div className="flex justify-evenly mt-[3rem]">
          <BorderlessButton type="leave" />
          <span className="bg-custom-medium-gray w-[0.1rem]"></span>
          <BorderlessButton type="logout" />
        </div>
      </div>

      <NavBar mode="MY_INFO" />
    </div>
  );
}
