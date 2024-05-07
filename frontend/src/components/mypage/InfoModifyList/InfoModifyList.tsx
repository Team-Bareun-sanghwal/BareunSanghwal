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
      title: 'nickname',
      desc: '닉네임',
      prevData: userData.nickname,
    },
    {
      title: 'gender',
      desc: '성별',
      prevData: userData.gender,
    },
    {
      title: 'job',
      desc: '직업',
      prevData: userData.job,
    },
    {
      title: 'birthDate',
      desc: '생일',
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
            desc={data.desc}
            prevData={data.prevData}
          />
        );
      })}
    </div>
  );
};
