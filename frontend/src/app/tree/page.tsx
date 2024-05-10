// import { useRouter } from 'next/navigation';
import Item from '@/components/point/Item/Item';
import { MyPoint } from '@/components/point/MyPoint/MyPoint';
import { $Fetch } from '@/apis';
import Tree from '@/components/tree/Tree';
import { RouteHome } from '@/components/tree/Button/RouteHome/RouteHome';
import { PopOver } from '@/components/common/PopOver/PopOver';
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
  // const router = useRouter();

  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products`,
    cache: 'no-cache',
  });

  console.log(response.data);
  // const exit = () => {
  //   router.back();
  // };

  return (
    <div>
      <div className="w-full h-screen overflow-hidden relative">
        <RouteHome />

        <Tree color="red" />
        <div className="absolute bottom-0 w-full gap-3 p-3">
          <div className="flex flex-col justify-center gap-4">
            <MyPoint />
            <PopOver />
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
