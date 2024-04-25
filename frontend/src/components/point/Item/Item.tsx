import { ShoppingBagIcon } from '@heroicons/react/24/outline';
import Image from 'next/image';
import Point from '../Point/Point';
interface IItemProps {
  name: string;
  introduction: string;
  description: string;
  price: number;
  iconPath: string;
}

const Item = ({
  name,
  introduction,
  description,
  price,
  iconPath,
}: IItemProps) => {
  return (
    <>
      <div className="flex border-solid rounded-lg shadow-md">
        <div className="flex p-6 w-full">
          <div className="content-center mx-6 ">
            <Image src={iconPath} alt="" width={36} height={36}></Image>
          </div>
          <div className="flex flex-col gap-2">
            <div className="flex">
              <div className="text-2xl mr-4">{name}</div>
              <Point point={price} />
            </div>
            <div className="text-sm w-full">{introduction}</div>
          </div>
        </div>
        <button className="ml-auto px-4 border-l-2 border-dotted">
          <ShoppingBagIcon width={16} height={16} />
        </button>
      </div>
    </>
  );
};

export default Item;
