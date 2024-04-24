const Point = ({ point }: { point: number }) => {
  return (
    <div className="flex border-solid border-4 border-custom-matcha p-2 text-2xl text-center content-center max-w-36 min-w-20 rounded-full">
      <div className="bg-custom-matcha w-8 h-8 rounded-full text-white text-center content-center">
        P
      </div>
      <p className="flex text-right ml-auto mr-1 text-custom-matcha">{point}</p>
    </div>
  );
};
export default Point;
