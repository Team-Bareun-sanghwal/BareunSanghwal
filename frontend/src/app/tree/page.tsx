// import { useRouter } from 'next/navigation';
import Item from '@/components/point/Item/Item';
import { MyPoint } from '@/components/point/MyPoint/MyPoint';
import { $Fetch } from '@/apis';
import Tree from '@/components/tree/Tree';
import { RouteHome } from '@/components/tree/Button/RouteHome/RouteHome';
import { PopOver } from '@/components/common/PopOver/PopOver';
import { Harvest } from '@/components/point/Harvest/Harvest';
import { Time } from '@/components/calendar/util';
import { treeConfig } from '@/components/tree/treeConfig';
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

export default async function Page() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products`,
    cache: 'no-cache',
  });
  const pointInfo = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/point`,
    cache: 'no-cache',
  });
  const treeInfo = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/tree`,
    cache: 'no-cache',
  });
  const { treeLevel, treeColor } = treeInfo.data;
  const { point, isHarvestedToday } = pointInfo.data;

  return (
    <div>
      <div className="w-full h-screen overflow-hidden relative">
        <RouteHome />
        <div className="flex flex-col absolute z-10 top-10 w-full items-center ">
          <div className=" bg-custom-dark-gray p-4 rounded-md">
            <div className="text-gray-300 text-md text-center">
              LEVEL {treeLevel}
            </div>
            <div className="text-custom-white text-xl text-center">
              {treeConfig[treeLevel - 1].name}
            </div>
          </div>
        </div>
        {!isHarvestedToday && <Harvest isHarvested={false} />}
        <Tree color={treeColor} level={treeLevel} time={Time()} />
        <div className="absolute bottom-0 w-full gap-3 p-3 ">
          <div className="flex flex-col justify-center gap-4">
            <MyPoint />
            {response?.data.products.map((item: IItem) => (
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
      </div>
    </div>
  );
}
