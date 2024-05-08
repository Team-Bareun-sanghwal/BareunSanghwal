'use client';
import { ShoppingBagIcon } from '@heroicons/react/24/outline';
import Image from 'next/image';
import Point from '../Point/Point';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { useOverlay } from '@/hooks/use-overlay';
import { $Fetch } from '@/apis';
interface IItemProps {
  keyname: string;
  name: string;
  introduction: string;
  description: string;
  price: number;
}
interface IReturn {
  path: string;
  mode: 'PURCHASE_STREAK' | 'PURCHASE_TREE' | 'PURCHASE_RECOVERY';
}

const getAttributes = (key: string) => {
  let path = '';
  let mode = '';
  if (key === 'gotcha_streak') {
    path = '/images/icon-item-streak-color.png';
    mode = 'PURCHASE_STREAK';
  } else if (key === 'recovery') {
    mode = 'PURCHASE_RECOVERY';
    path = '/images/icon-item-recovery.png';
  } else {
    path = '/images/icon-item-tree.png';
    mode = 'PURCHASE_TREE';
  }
  return { path, mode } as IReturn;
};

interface IPurchase {
  keyname: string;
  name: string;
  introduction: string;
  description: string;
  price: number;
  mode: 'PURCHASE_STREAK' | 'PURCHASE_TREE' | 'PURCHASE_RECOVERY';
}
const Purchase = async (key: string) => {
  const path =
    key == 'gotcha_streak'
      ? 'color/streak'
      : key == 'gotcha_tree'
        ? 'color/tree'
        : 'recovery';
  const response = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/${path}`,
    cache: 'no-cache',
  });
  return response;
};

const Item = ({
  keyname,
  name,
  introduction,
  description,
  price,
}: IItemProps) => {
  const overlay = useOverlay();
  const purchase = ({
    keyname,
    name,
    introduction,
    description,
    price,
    mode,
  }: IPurchase) => {
    overlay.open(({ isOpen, close }) => (
      <BottomSheet
        description={description}
        mode={mode}
        onClose={close}
        onConfirm={() => result(keyname)}
        open={isOpen}
        title={introduction}
      />
    ));
  };
  const result = (key: string) => {
    const mode =
      key === 'gotcha_streak'
        ? 'PURCHASE_STREAK'
        : key === 'gotcha_tree'
          ? 'PURCHASE_TREE'
          : 'PURCHASE_RECOVERY';
    Purchase(key)
      .then((response) => {
        //구매 성공
        if (response.status === 200) {
          console.log(response);
          overlay.open(({ isOpen, close }) => (
            <BottomSheet
              description="구매 완료"
              mode={mode}
              onClose={close}
              onConfirm={close}
              open={isOpen}
              title="야호!"
            />
          ));
          // 잔액 부족
        } else if (response.status === 402) {
          overlay.open(({ isOpen, close }) => (
            <BottomSheet
              description="잔액이 부족합니다."
              mode="NEGATIVE"
              onClose={close}
              open={isOpen}
              title="앗!"
            />
          ));
        } else {
          overlay.open(({ isOpen, close }) => (
            <BottomSheet
              description="알 수 없는 이유로 구매에 실패했어요"
              mode="NEGATIVE"
              onClose={close}
              open={isOpen}
              title="ERROR"
            />
          ));
        }
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const { path, mode } = getAttributes(keyname);
  return (
    <>
      <div className="flex w-full border-solid rounded-lg shadow-md bg-custom-white">
        <div className="flex p-6 w-full">
          <div className="content-center">
            <Image src={path} alt="" width={36} height={36} />
          </div>
          <div className="flex flex-col gap-2 ml-4">
            <div className="flex">
              <div className="text-2xl mr-4 content-center">{name}</div>
              <Point point={price} />
            </div>
            <div className="text-sm w-full">{introduction}</div>
          </div>
        </div>
        <button
          onClick={() =>
            purchase({ keyname, name, introduction, description, price, mode })
          }
          className="ml-auto px-4 border-l-2 border-dotted"
        >
          <ShoppingBagIcon width={16} height={16} />
        </button>
      </div>
    </>
  );
};

export default Item;
