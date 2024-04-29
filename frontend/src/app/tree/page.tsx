'use client';
import Tree from '@/components/tree/Tree';
import { useRouter } from 'next/navigation';
import { useState, useEffect } from 'react';
import { ItemListResponse } from '../mock';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-lego.json';
const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });
import Item from '@/components/point/Item/Item';
export default function Page() {
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);
  const [showLoader, setShowLoader] = useState(false);

  /*useEffect(() => {
    if (isLoading) {
      const timer = setTimeout(() => {
        setIsLoading(false);
        setShowLoader(false);
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [isLoading]);*/
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
          <button onClick={router.back} className="absolute z-10 m-4 text-lg">
            {'<'} 뒤로가기
          </button>
          <Tree color="red" />
          <div className="absolute bottom-0 w-full gap-3 p-3">
            <div className="flex flex-col justify-center gap-4">
              <Item
                name={ItemListResponse.recovery.name}
                introduction={ItemListResponse.recovery.introduction}
                description={ItemListResponse.recovery.description}
                price={ItemListResponse.recovery.price}
                iconPath="/images/icon-item-recovery.png"
              />
              <Item
                name={ItemListResponse.gotcha_streak.name}
                introduction={ItemListResponse.gotcha_streak.introduction}
                description={ItemListResponse.gotcha_streak.description}
                price={ItemListResponse.gotcha_streak.price}
                iconPath="/images/icon-item-streak-color.png"
              />
              <Item
                name={ItemListResponse.gotcha_tree.name}
                introduction={ItemListResponse.gotcha_tree.introduction}
                description={ItemListResponse.gotcha_tree.description}
                price={ItemListResponse.gotcha_tree.price}
                iconPath="/images/icon-item-tree.png"
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
