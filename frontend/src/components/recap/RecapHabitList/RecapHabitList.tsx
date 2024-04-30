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
  // const processedData = rateByMemberHabitList.map((data, index) => {
  //   return {
  //     ...data,
  //     color: `text-[${
  //       colorArr[
  //         Math.round((5 / rateByMemberHabitList.length) * (index + 1) - 1)
  //       ]
  //     }]`,
  //   };
  // });
  // console.table(processedData);

  return (
    <div className="w-full flex justify-between items-center h-[50rem] text-white">
      <div className="pl-[5rem] py-[3rem] flex flex-col justify-around h-[50rem] w-3/5">
        {rateByMemberHabitList.map((data, index) => {
          const colorIdx = Math.round(
            (5 / rateByMemberHabitList.length) * (index + 1) - 1,
          );
          const textColor = `text-[${colorArr[colorIdx]}]`;
          console.log(textColor);

          return <ColoredRecapTitle title={data.name} colorIdx={index} />;
        })}
      </div>
      <div className="h-[50rem] w-2/5 flex items-center">
        <Image
          src="/images/icon-target.png"
          alt="habit-list"
          width={200}
          height={200}
        />
      </div>
    </div>
  );
};
