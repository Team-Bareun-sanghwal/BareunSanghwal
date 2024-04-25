const Point = ({ point }: { point: number }) => {
  return (
    <div className="flex border-solid border-2 border-custom-matcha p-1 text-l text-center content-center max-w-24 min-w-16 rounded-full">
      <div className="bg-custom-matcha w-6 h-6 rounded-full text-white text-center content-center mr-2">
        P
      </div>
      <p className="flex text-right ml-auto mr-1 text-custom-matcha">{point}</p>
    </div>
  );
};
export default Point;
