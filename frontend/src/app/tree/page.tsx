
import { $Fetch } from '@/apis';
import Tree from '@/components/tree/Tree';
import { RouteHome } from '@/components/tree/Button/RouteHome/RouteHome';
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

export default async function Page() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products`,
    cache: 'default',
  });
  const pointInfo = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/point`,
    cache: 'default',
  });
  const treeInfo = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/tree`,
    cache: 'default',
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
        <Tree color={treeColor} level={treeLevel} time={Time()} ItemList={response.data.products} />
      </div>
    </div>
  );
}
