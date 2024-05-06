import Image from 'next/image';
import { ColoredRecapTitle } from '../ColoredRecapTitle/ColoredRecapTitle';

interface IDataType {
  name: string;
  missCount: number;
  actionCount: number;
  ratio: number;
}

export const RecapHabitList = ({
  rateByMemberHabitList,
}: {
  rateByMemberHabitList: IDataType[];
}) => {
  return (
    <div className="w-full flex justify-between items-center h-[50rem] text-white">
      <div className="w-full h-[50rem] pl-[4rem] flex flex-col justify-center gap-[6rem]">
        {rateByMemberHabitList.map((data, index) => {
          const colorIdx = Math.round(
            (5 / rateByMemberHabitList.length) * (index + 1) - 1,
          );

          return (
            <ColoredRecapTitle
              key={index}
              title={data.name}
              colorIdx={colorIdx}
            />
          );
        })}
      </div>
      <div className="w-[50rem] h-[50rem] flex items-center justify-end overflow-hidden">
        <Image
          className="w-full animationZoom"
          src="/images/icon-target.png"
          alt="habit-list"
          width={150}
          height={150}
        />
      </div>
    </div>
  );
};
