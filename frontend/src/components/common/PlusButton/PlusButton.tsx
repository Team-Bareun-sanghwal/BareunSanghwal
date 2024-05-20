import { PlusIcon } from '@heroicons/react/24/solid';

export const PlusButton = ({ onClick }: { onClick: () => void }) => {
  return (
    <button
      className="w-full h-[4rem] rounded-[1rem] bg-custom-light-gray flex items-center justify-center"
      onClick={onClick}
    >
      <PlusIcon className="w-[2.4rem] h-[2.4rem]" />
    </button>
  );
};
