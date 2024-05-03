'use client';
import Tree from '@/components/tree/Tree';
import { useRouter } from 'next/navigation';
import { useState, useEffect } from 'react';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-lego.json';
import Item from '@/components/point/Item/Item';
import { $Fetch } from '@/apis';
import { ItemListResponseSample } from '../mock';
interface IItemList {
  products: IItem[];
}

interface IItem {
  key: string;
  name: string;
  introduction: string;
  description: string;
  price: number;
}
interface IItemResponse {
  key: string;
  name: string;
  introduction: string;
  description: string;
  price: number;
}

const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

export default function Page() {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);
  const [showLoader, setShowLoader] = useState(true);
  const [ItemListResponse, setItemListResponse] = useState<IItemList>({
    products: [],
  });
  useEffect(() => {
    setItemListResponse(ItemListResponseSample);
    fetch(`${process.env.NEXT_PUBLIC_BASE_URL}/products`, {
      method: 'GET',
      cache: 'no-cache',
      headers: {
        'Content-Type': 'application/json',
        Authorization: process.env.NEXT_PUBLIC_ACCESS_TOKEN as string,
      },
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setItemListResponse(data);
        console.log(data);
      })
      .catch((error) => {
        console.error('ERR:', error);
      });
    if (isLoading) {
      const timer = setTimeout(() => {
        setIsLoading(false);
        setShowLoader(false);
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [isLoading]);

  const Exit = () => {
    setIsLoading(true);
    setShowLoader(true);
    setTimeout(() => {
      router.back();
    }, 2000);
  };
  return (
    <div>
      {showLoader ? (
        <div className="flex flex-col w-full h-screen justify-center items-center content-center">
          <LottieBox
            loop
            animationData={lottieJson}
            play
            className="w-[10rem] h-[10rem]"
          />
        </div>
      ) : (
        <div className="w-full h-screen overflow-hidden relative">
          <button onClick={Exit} className="absolute z-10 m-4 text-lg">
            {'<'} 뒤로가기
          </button>
          <Tree color="red" />
          <div className="absolute bottom-0 w-full gap-3 p-3">
            <div className="flex flex-col justify-center gap-4">
              {ItemListResponseSample.products?.map((item) => (
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
      )}
    </div>
  );
}
