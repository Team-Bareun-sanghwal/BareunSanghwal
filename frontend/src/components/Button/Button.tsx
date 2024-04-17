interface ButtonProps {
  isActivated: boolean;
  label: string;
  onClick?: () => void;
}

export const Button = ({ isActivated, label, ...props }: ButtonProps) => {
  const mode = isActivated ? 'bg-custom-matcha' : 'bg-custom-medium-gray';
  return (
    <button
      className={`${mode} ${isActivated && 'hover:bg-custom-green'} w-[34rem] h-[5rem] text-custom-white custom-semibold-text rounded-[0.8rem]`}
      {...props}
    >
      {label}
    </button>
  );
};
