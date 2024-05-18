'use client';
import Point from '../Point/Point';
import { MyPoint } from '../MyPoint/MyPoint';
import { $Fetch } from '@/apis';
import Item from '../Item/Item';
export async function getServerSideProps() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products`,
    cache: 'default',
  });
  return {
    props: {
      response,
    },
  };
}

interface IItem {
  key: string;
  name: string;
  introduction: string;
  description: string;
  price: number;
}

interface IItemList {
  products: IItem[];
}

export const ItemList = ({ response }: { response?: IItemList }) => {
  return (
    <div className="absolute bottom-0 w-full gap-3 p-3">
      <div className="flex flex-col justify-center gap-4">
        <MyPoint />
        {response?.products.map((item: IItem) => (
          <Item
            key={item.key}
            keyname={item.key}
            name={item.name}
            introduction={item.introduction}
            description={item.description}
            price={item.price}
          />
        ))}
      </div>
    </div>
  );
};
