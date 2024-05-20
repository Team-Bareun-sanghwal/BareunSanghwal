import { ColoredSentence } from '@/components/common/ColoredSentence/ColoredSentence';

interface IPropType {
  textFront: string;
  textMiddle: string;
  textBack: string;
}

export const GradientBar = ({ textFront, textMiddle, textBack }: IPropType) => {
  return (
    <div>
      <div className="w-full bg-slate-200 h-[3rem] flex items-center justify-center border custom-gradient-border rounded-[2rem]">
        <ColoredSentence
          textFront={textFront}
          textMiddle={textMiddle}
          textBack={textBack}
        />
      </div>
    </div>
  );
};
