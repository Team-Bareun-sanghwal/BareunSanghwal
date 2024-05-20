interface IPropType {
  defaultValue: string;
  setDefaultValue: (newValue: string) => void;
}

export const DatePicker = ({ defaultValue, setDefaultValue }: IPropType) => {
  return (
    <div className="text-center">
      <input
        className="bg-custom-light-gray text-custom-black relative rounded-full mx-auto min-w-[20rem] px-[2rem] py-[1rem] custom-medium-text text-center"
        type="date"
        defaultValue={defaultValue}
        onChange={(e) => {
          setDefaultValue(e.target.value);
        }}
      />
      <p className="text-custom-dark-gray custom-light-text text-center mt-[1rem]">
        클릭해서 날짜를 설정해주세요!
      </p>
    </div>
  );
};
