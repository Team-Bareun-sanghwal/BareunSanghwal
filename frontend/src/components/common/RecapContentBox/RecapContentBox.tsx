import Image from 'next/image';

interface IRecapData {
  recapId: number;
  image: string;
  period: Date;
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
    <div className="relative cursor-pointer" onClick={() => {}}>
      <div className="size-full bg-custom-black-with-opacity absolute"></div>
      <p className="absolute bottom-[0.5rem] right-[0.5rem] custom-semibold-text text-custom-white">
        {dateText}
      </p>

      <Image
        src={imgSrc}
        width={100}
        height={100}
        alt={'clock'}
        className="size-full object-cover"
      ></Image>
    </div>
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
                  <li>
                    <RecapImageContent
                      imgSrc="/images/icon-clock.png"
                      dateText={`${recap.period.getMonth() + 1}월`}
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
