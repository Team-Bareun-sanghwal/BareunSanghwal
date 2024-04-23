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
    <div className="w-fit flex justify-start">
      <pre className="custom-light-text text-custom-black">{textFront}</pre>
      <pre className="text-custom-yellow-green custom-emphasize-text">
        {textMiddle}
      </pre>
      <pre className="custom-light-text text-custom-black">{textBack}</pre>
    </div>
  );
};
