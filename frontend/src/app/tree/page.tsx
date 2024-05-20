import { $Fetch } from '@/apis';
import Tree from '@/components/tree/Tree';
import { RouteHome } from '@/components/tree/Button/RouteHome/RouteHome';
import { Harvest } from '@/components/point/Harvest/Harvest';
import { Time } from '@/components/calendar/util';
import { treeConfig } from '@/components/tree/treeConfig';
import { MyPoint } from '@/components/point/MyPoint/MyPoint';
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

  const getTreeColor = (treeColor: string) => {
    switch (treeColor) {
      case 'red':
        return '#ff0000';
      case ' green':
        return '#008000';
      case 'blue':
        return '#0000ff';
      case 'yellow':
        return '#ffff00';
      case 'orange':
        return '#ffa500';
      case 'purple':
        return '#800080';
      case 'gold':
        return '#ffd700';
      case 'silver':
        return '#c0c0c0';
      case 'cotton_candy':
        return '#aee5ff';
      case 'cherry_blossom':
        return '#ffc0cb';
      default:
        return '#008000';
    }
  };
  return (
    <div className="w-full min-h-screen overflow-hidden relative">
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
      {!isHarvestedToday ? <Harvest isHarvested={false} /> : <MyPoint />}

      <Tree
        color={getTreeColor(treeColor)}
        level={treeLevel}
        time={Time()}
        ItemList={response.data.products}
      />
    </div>
  );
}
