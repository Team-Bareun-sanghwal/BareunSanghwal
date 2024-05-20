interface IPropType {
  collectedStar: number;
}

export const RecapStars = ({ collectedStar }: IPropType) => {
  return (
    <>
      <div className="relative w-full h-[50rem] flex flex-col justify-center items-center bg-[url('/images/icon-galaxy.png')] bg-no-repeat bg-center">
        <div className="relative w-[33rem] h-[33rem]">
          {Array.from({ length: collectedStar }, (_, index) => (
            <div
              key={index}
              style={{
                background: `url(${'/images/icon-star.png'}) no-repeat center center`,
                backgroundPosition: 'right',
                backgroundSize: '2rem',
                clipPath: `polygon(50% 50%, 100% 0, 100% 100%)`,
                transform: `rotate(${((360 / collectedStar) * index - 90) % 360}deg)`,
                transformOrigin: 'center',
                position: 'absolute',
                width: '100%',
                height: '100%',
              }}
            />
          ))}
        </div>
        <div className="absolute text-center">
          <p className="text-white custom-light-text">별을 총</p>
          <p className="text-custom-yellow-green custom-bold-text">
            {collectedStar}개
          </p>
          <p className="text-white custom-light-text">모았어요!</p>
        </div>
      </div>
      <p className="text-custom-dark-gray custom-light-text text-center">
        별은 모든 해빗을 달성한 날에 얻을 수 있어요
      </p>
    </>
  );
};
