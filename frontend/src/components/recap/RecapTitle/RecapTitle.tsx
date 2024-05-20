interface IPropType {
  title: string;
}

export const RecapTitle = ({ title }: IPropType) => {
  return (
    <div className="w-full h-14 flex items-center">
      <div className="w-2 h-full mr-5 bg-custom-yellow-green" />
      <p className="text-white custom-bold-text h-fit">{title}</p>
    </div>
  );
};
