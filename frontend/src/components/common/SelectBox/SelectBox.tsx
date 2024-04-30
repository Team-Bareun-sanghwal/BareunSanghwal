'use client';

interface IOptionType {
  key: string;
  value: string;
}

interface SelectBoxProps {
  label?: string;
  options: IOptionType[];
  defaultValue: string;
  setDefaultValue: (newValue: string) => void;
}

export const SelectBox = ({
  label,
  options,
  defaultValue,
  setDefaultValue,
}: SelectBoxProps) => {
  return (
    <section className="w-full flex flex-col gap-[1rem]">
      {label && (
        <label className="custom-semibold-text text-custom-black">
          {label}
        </label>
      )}

      <ul className="rounded-[0.8rem] list-none flex border-[0.15rem] border-custom-dark-gray custom-light-text overflow-hidden">
        {options.map((option, index) => {
          return (
            <li
              key={`option-${index}`}
              className={`${index !== options.length - 1 && 'border-r-[0.15rem]'} ${defaultValue === option.key ? 'bg-custom-dark-gray' : 'bg-none'} ${defaultValue === option.key ? 'text-custom-white' : 'text-custom-black'} border-custom-dark-gray py-[0.8rem] text-center flex-grow cursor-pointer`}
              onClick={() => setDefaultValue(option.key)}
            >
              {option.value}
            </li>
          );
        })}
      </ul>
    </section>
  );
};
