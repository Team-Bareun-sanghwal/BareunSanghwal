const Point = ({ point }: { point: number }) => {
  return (
    <div className="flex  border-custom-matcha bg-white p-1 text-l text-center content-center max-w-24 min-w-16 h-10 rounded-full">
      {point === null ? (
        <div className="w-full font-extrabold text-center">FREE</div>
      ) : (
        <>
          <div className="bg-custom-matcha w-6 h-6 rounded-full text-white text-center content-center mr-2">
            P
          </div>
          <p className="flex text-right ml-auto mr-2 text-custom-matcha">
            {point}
          </p>
        </>
      )}
    </div>
  );
};
export default Point;
