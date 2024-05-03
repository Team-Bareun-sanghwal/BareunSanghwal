import Image from 'next/image';
export const DailyPhrase = ({ phrase }: { phrase: string }) => {
  return (
    <>
      <div className="flex justify-between overflow-hidden items-center m-4 h-24 bg-custom-light-gray text-custom-dark-gray text-xl rounded-md ">
        <p className="ml-6">{phrase}</p>
        <div className="rotate-[236deg] mb-10">
          <Image src="/images/icon-pencil.png" alt="" width={80} height={80} />
        </div>
      </div>
    </>
  );
};
