import { ColoredSentence } from '@/components/common/ColoredSentence/ColoredSentence';

interface IDataProps {
  whole_days: number;
  streak_days: number;
  star_days: number;
  longest_streak: number;
}

export const ReportOnlyText = ({
  whole_days,
  streak_days,
  star_days,
  longest_streak,
}: IDataProps) => {
  return (
    <div className="w-full bg-custom-light-gray px-[1.5rem] py-[1rem] flex flex-col gap-[1rem] rounded-[1rem]">
      <ColoredSentence
        textFront="지금까지 "
        textMiddle={whole_days.toString()}
        textBack="일 동안 바른생활을 이용하면서"
      />
      <ColoredSentence
        textFront="총 "
        textMiddle={streak_days.toString()}
        textBack="일 동안 스트릭을 달성하고,"
      />
      <ColoredSentence
        textFront="총 "
        textMiddle={star_days.toString()}
        textBack="개의 별을 모았어요!"
      />
      <ColoredSentence
        textFront="가장 오래 유지한 스트릭은 "
        textMiddle={longest_streak.toString()}
        textBack="일이에요"
      />
    </div>
  );
};
