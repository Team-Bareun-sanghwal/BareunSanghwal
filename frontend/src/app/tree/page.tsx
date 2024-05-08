'use client';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-lego.json';
import Item from '@/components/point/Item/Item';
import { MyPoint } from '@/components/point/MyPoint/MyPoint';
import { $Fetch } from '@/apis';
const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

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

export default function Page() {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);
  const [showLoader, setShowLoader] = useState(false);
  const [ItemListResponse, setItemListResponse] = useState<IItemList>({
    products: [],
  });

  // 로딩 지연을 처리하는 useEffect
  useEffect(() => {
    if (isLoading) {
      const timer = setTimeout(() => {
        setShowLoader(false);
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [isLoading]);

  useEffect(() => {
    $Fetch({
      method: 'GET',
      url: `${process.env.NEXT_PUBLIC_BASE_URL}/products`,
      cache: 'no-cache',
    })
      .then((data) => {
        setItemListResponse(data);
        setIsLoading(false);
      })
      .catch((error) => {
        console.error('ERR:', error);
        setIsLoading(false);
      });
  }, []);

  const exit = () => {
    setShowLoader(true);
    router.back();
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
          <button onClick={exit} className="absolute z-10 m-4 text-lg">
            {'<'} 뒤로가기
          </button>
          <div className="absolute bottom-0 w-full gap-3 p-3">
            <div className="flex flex-col justify-center gap-4">
              <MyPoint />
              {ItemListResponse.products.map((item) => (
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
