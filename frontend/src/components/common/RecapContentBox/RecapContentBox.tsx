import Image from 'next/image';
import Link from 'next/link';

interface IRecapData {
  recapId: number;
  image: string;
  period: string;
}

interface IRecapDataPerYear {
  year: number;
  recapList: IRecapData[];
}

interface IRecapContentBoxProps {
  recapTotalData: IRecapDataPerYear[];
}

// // Recap에서 쓰이는 컨텐츠
// // 클릭 이벤트 발생 시 리캡으로 이동(recapId를 활용하여 동적 라우팅)
const RecapImageContent = ({
  imgSrc,
  dateText,
  recapId,
}: {
  imgSrc: string;
  dateText: string;
  recapId: number;
}) => {
  return (
    <Link href={`/recap/${recapId}`} className="relative">
      <div className="size-full bg-custom-black-with-opacity absolute"></div>
      <p className="px-[0.5rem] absolute bottom-0 right-0 custom-semibold-text bg-custom-black-with-opacity text-custom-white">
        {dateText}
      </p>

      <Image
        src={imgSrc}
        width={100}
        height={100}
        alt={'clock'}
        className="size-full object-fill"
      ></Image>
    </Link>
  );
};

export const RecapContentBox = ({ recapTotalData }: IRecapContentBoxProps) => {
  return (
    <section className="w-full flex flex-col gap-[3rem]">
      {recapTotalData.map((recapYearData, index) => {
        return (
          <section
            key={`year-${index}`}
            className="w-full flex flex-col gap-[1rem]"
          >
            <label className="custom-semibold-text text-custom-black">
              {`${recapYearData.year}년`}
            </label>

            <ul className="w-full grid grid-cols-3">
              {recapYearData.recapList.map((recap, index) => {
                return (
                  <li key={index}>
                    <RecapImageContent
                      imgSrc="/images/icon-clock.png"
                      dateText={`${recap.period.split('-')[1]}월`}
                      recapId={recap.recapId}
                    />
                  </li>
                );
              })}
            </ul>
          </section>
        );
      })}
    </section>
  );
};
