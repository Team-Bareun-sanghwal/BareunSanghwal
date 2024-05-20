import { BookmarkIcon } from '@heroicons/react/24/solid';

interface GuideTextProps {
  text: string;
}

export const GuideText = ({ text }: GuideTextProps) => {
  return (
    <p className="w-full flex gap-[0.5rem] items-center text-custom-light-green font-bold text-[1.2rem]">
      <BookmarkIcon className="w-[1.8rem] h-[1.8rem]" />
      {text}
    </p>
  );
};
