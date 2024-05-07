import { InfoModify } from '../InfoModify/InfoModify';

interface IPropType {
  nickname: string;
  birthDate: string;
  gender: string;
  job: string;
}

export const InfoModifyList = ({ userData }: { userData: IPropType }) => {
  const mypageArr = [
    {
      title: '닉네임',
      prevData: userData.nickname,
    },
    {
      title: '성별',
      prevData: userData.gender,
    },
    {
      title: '직업',
      prevData: userData.job,
    },
    {
      title: '생일',
      prevData: userData.birthDate,
    },
  ];

  return (
    <div className="flex flex-col gap-[4rem]">
      {mypageArr.map((data) => {
        return (
          <InfoModify
            key={data.title}
            title={data.title}
            prevData={data.prevData}
          />
        );
      })}
    </div>
  );
};
