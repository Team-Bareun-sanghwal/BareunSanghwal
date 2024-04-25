interface IHabitDayData {
  name: string;
  habitId: number;
  habitDayList: number[];
}

interface IHabitDayChartProps {
  habitList: IHabitDayData[];
}

const HabitDayBox = ({
  isChecked,
  koreanDayName,
}: {
  isChecked: boolean;
  koreanDayName: string;
}) => {
  return (
    <div className="w-full h-[3rem] my-auto px-[0.3rem] py-[0.7rem]">
      {isChecked && (
        <div className="w-full h-full bg-custom-light-green rounded-[1rem] text-[1rem] font-semibold flex items-center justify-center">
          {koreanDayName}
        </div>
      )}
    </div>
  );
};

const HabitDayRow = ({
  name,
  dayList,
}: {
  name: string;
  dayList: number[];
}) => {
  return (
    <div className="flex flex-col gap-[0.5rem]">
      <label className="text-[1.2rem] font-bold">{name}</label>

      <div className="flex divide-dashed divide-x-[0.1rem] divide-custom-medium-gray">
        <HabitDayBox isChecked={dayList.includes(1)} koreanDayName="월" />
        <HabitDayBox isChecked={dayList.includes(2)} koreanDayName="화" />
        <HabitDayBox isChecked={dayList.includes(3)} koreanDayName="수" />
        <HabitDayBox isChecked={dayList.includes(4)} koreanDayName="목" />
        <HabitDayBox isChecked={dayList.includes(5)} koreanDayName="금" />
        <HabitDayBox isChecked={dayList.includes(6)} koreanDayName="토" />
        <HabitDayBox isChecked={dayList.includes(7)} koreanDayName="일" />
      </div>
    </div>
  );
};

export const HabitDayChart = ({ habitList }: IHabitDayChartProps) => {
  return (
    <section className="w-full flex flex-col gap-[1rem]">
      <label className="custom-semibold-text text-custom-black">
        현재 진행 중인 해빗의 요일
      </label>

      {habitList?.map((habit, index) => {
        return (
          <HabitDayRow
            key={`habitDay-${index}`}
            name={habit.name}
            dayList={habit.habitDayList}
          />
        );
      })}
    </section>
  );
};
