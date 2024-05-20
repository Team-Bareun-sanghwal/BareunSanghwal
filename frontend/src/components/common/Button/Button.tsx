interface ButtonProps {
  isActivated: boolean;
  label: string;
  onClick?: () => void;
  disabled?: boolean;
}

export const Button = ({ isActivated, label,disabled, ...props }: ButtonProps) => {
  const mode = isActivated ? 'bg-custom-matcha' : 'bg-custom-medium-gray';
  return (
    <button
      className={`${mode} ${isActivated && 'hover:bg-custom-green'} w-full h-[5rem] text-custom-white custom-semibold-text rounded-[0.8rem] }`}
      {...props}
    >
      {label}
    </button>
  );
};
