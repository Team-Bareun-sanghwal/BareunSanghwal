interface IColoredSentenceProps {
  textFront: string;
  textMiddle: string;
  textBack: string;
}

export const ColoredSentence = ({
  textFront,
  textMiddle,
  textBack,
}: IColoredSentenceProps) => {
  return (
    <div className="w-full flex justify-start">
      <pre className="text-custom-black">{textFront}</pre>
      <pre className="text-custom-yellow-green custom-emphasize-text">
        {textMiddle}
      </pre>
      <pre className="text-custom-black">{textBack}</pre>
    </div>
  );
};
