import Image from 'next/image';

interface IPropType {
  keyword: string;
}

export const RecapKeyword = ({ keyword }: IPropType) => {
  return (
    <div className="w-full h-[50rem] flex flex-col items-center justify-center">
      <p className="text-custom-yellow-green custom-bold-text"># {keyword}</p>
      <p className="text-white custom-light-text mb-[4rem]">
        해빗을 기록할 때 이 키워드를 많이 쓰셨네요!
      </p>
      <Image
        src="/images/icon-keyword.png"
        alt="keyword"
        width={300}
        height={300}
      />
    </div>
  );
};
