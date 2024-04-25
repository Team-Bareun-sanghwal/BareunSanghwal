interface IHabit {
  name: string;
  habitId: number;
}

interface IHabitSearchBoxProps {
  searchedList: IHabit[];
}

const HabitNameButton = ({ name }: { name: string }) => {
  return (
    <button className="custom-medium-text px-[1rem] py-[0.5rem] bg-custom-light-gray rounded-[1rem]">
      {name}
    </button>
  );
};

export const HabitSearchBox = ({ searchedList }: IHabitSearchBoxProps) => {
  return (
    <section className="w-full flex flex-col gap-[1.5rem]">
      <label className="custom-semibold-text text-custom-black">
        해빗을 검색해주세요
      </label>

      <input
        type="text"
        className="w-full px-[1.5rem] py-[0.7rem] rounded-[3rem] bg-transparent custom-medium-text outline-none border-[0.1rem] border-custom-medium-gray"
      ></input>

      <div className="flex gap-[1rem] flex-wrap -mt-[0.5rem]">
        {searchedList?.map((searchedHabit, index) => {
          return (
            <HabitNameButton key={`habit-${index}`} name={searchedHabit.name} />
          );
        })}
      </div>
    </section>
  );
};
