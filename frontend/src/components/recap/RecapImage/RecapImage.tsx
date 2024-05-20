import Image from 'next/image';

interface IPropType {
  image: string;
}

export const RecapImage = ({ image }: IPropType) => {
  return (
    <div className="flex flex_col h-full justify-center items-center overflow-hidden">
      <div className="w-[30rem] h-[30rem] flying-div">
        <Image
          src={image}
          alt="recap-image"
          width={300}
          height={300}
          className="object-cover w-full h-full"
        />
      </div>
    </div>
  );
};
