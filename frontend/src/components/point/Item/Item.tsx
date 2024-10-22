'use client';
import { motion, AnimatePresence } from 'framer-motion';
import { ShoppingBagIcon } from '@heroicons/react/24/outline';
import Image from 'next/image';
import Point from '../Point/Point';
import { BottomSheet } from '@/components/common/BottomSheet/BottomSheet';
import { useOverlay } from '@/hooks/use-overlay';
import { $Fetch } from '@/apis';
import ColoredText from '../ColoredText/ColoredText';
import Pallete from '../Pallete/Pallete';
import { useRouter } from 'next/navigation';
import { Recovery } from '../Recovery/Recovery';
import { useEffect, useState } from 'react';
import { CreateOverlayElement } from '@/hooks/use-overlay/types';
interface IItemProps {
  keyname: 'gotcha_streak' | 'gotcha_tree' | 'recovery' | 'none';
  name: string;
  introduction: string;
  description: string;
  price: number;
  selectedItem: 'gotcha_streak' | 'gotcha_tree' | 'recovery' | 'none';
  setSelectedItem : React.Dispatch<React.SetStateAction<'gotcha_streak' | 'gotcha_tree' | 'recovery' | 'none'>>;
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
    path = '/images/icon-item-recovery.png';
    mode = 'PURCHASE_RECOVERY';
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
// 구매
const Purchase = async (key: string) => {
  const path =
    key == 'gotcha_streak'
      ? 'color/streak'
      : key == 'gotcha_tree'
        ? 'color/tree'
        : 'recovery';
  const response = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/products/${path}`,
    cache: 'default',
  });
  return response;
};

const getRecoveryInfo = async () => {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/recovery-count`,
    cache: 'default',
  });
  return response;
};

const Item = ({
  keyname,
  name,
  introduction,
  description,
  price,
  selectedItem,
  setSelectedItem,
}: IItemProps) => {
  const router = useRouter();
  const overlay = useOverlay();
  const [displayContent, setDisplayContent] = useState<'gotcha_streak' | 'gotcha_tree' | 'recovery' | 'none'>(keyname);

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      setDisplayContent(selectedItem);
    }, 300); 
    return () => clearTimeout(timeoutId);
  }, [selectedItem]);
  const SelectRecovery = () => {
    overlay?.open(({ isOpen, close }) => (
      <>
        <Recovery
          title="스트릭 복구"
          description="스트릭을 복구하시겠습니까?"
          open={isOpen}
          onClose={close}
          onConfirm={() => result('recovery')}
        />
      </>
    ));
  };
  const purchase = ({
    keyname,
    name,
    introduction,
    description,
    price,
    mode,
  }: IPurchase) => {
    if (keyname === 'recovery') {
      SelectRecovery();
    } else {
      overlay?.open(({ isOpen, close }) => (
        <BottomSheet
          description={description}
          mode={mode}
          onClose={close}
          onConfirm={() => result(keyname)}
          open={isOpen}
          title={introduction}
        />
      ));
    }
  };
  const result = (key: string) => {
    Purchase(key)
      .then((response) => {
        if (response.status === 200) {
          overlay?.open(({ isOpen, close }) => (
            <BottomSheet
              description=""
              mode="NONE"
              onConfirm={() => {
                close();
                router.refresh();
              }}
              open={isOpen}
              title="구매 완료!"
            >
              {key == 'gotcha_streak' && (
                <>
                  <Pallete color={response.data.streakColorName} />
                  <ColoredText color={response.data.streakColorName} />
                </>
              )}
              {key == 'gotcha_tree' && (
                <>
                  <ColoredText color={response.data.treeColorName} />
                </>
              )}
              {key == 'recovery' && (
                <>
                  <div className="flex justify-center text-3xl ">
                    <span className="text-2xl">스트릭 개수가 </span>
                    <span className="text-2xl text-custom-matcha mx-2">
                      {response.data.paidRecoveryCount}
                    </span>
                    <span className="text-2xl"> 개가 되었어요! </span>
                  </div>
                </>
              )}
            </BottomSheet>
          ));
          // 잔액 부족
        } else if (response.status === 422) {
          overlay?.open(({ isOpen, close }) => (
            <BottomSheet
              description="잔액이 부족합니다."
              mode="NEGATIVE"
              onClose={close}
              open={isOpen}
              title="앗!"
            />
          ));
        } else {
          overlay?.open(({ isOpen, close }) => (
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
      .catch(() => {
        overlay?.open(({ isOpen, close }) => (
          <BottomSheet
            description="알 수 없는 이유로 구매에 실패했어요"
            mode="NEGATIVE"
            onClose={close}
            open={isOpen}
            title="ERROR"
          />
        ));
      });
  };
  const { path, mode } = getAttributes(keyname);
  return (
    <>
      <AnimatePresence>
        <motion.div
          layout
          initial={{ opacity: 0, height: 0 }}
          animate={{ opacity: 1, height: 'auto', transition: { duration: 0.3 } }}
          exit={{ opacity: 0, height: 0, transition: { duration: 0.3 } }}
          className="flex w-full border-solid rounded-lg shadow-md bg-custom-white overflow-hidden"
          
        >
          <div
          className="flex p-4 w-full"
          onClick={() => {
            setTimeout(() => {
              if (selectedItem === keyname) {
                setSelectedItem('none');
              } else {
                setSelectedItem(keyname);
              }
            }, 300);
          }}>
            <div className="flex-shrink-0" style={{ width: '48px', height: '48px' }}>
              <Image src={path} alt="" width={48} height={48} className="block" />
            </div>
            <div className="flex flex-col gap-2 ml-4 w-full">
              <div className="flex justify-between items-center">
                <div className="text-2xl mr-4">{name}</div>
                <Point point={price} />
              </div>
              <div className="text-sm">{introduction}</div>
              {displayContent === keyname && keyname === selectedItem && (
                <motion.div
                  layout
                  initial={{ opacity: 0, height: 0 }}
                  animate={{ opacity: 1, height: 'auto', transition: { duration: 0.3 } }}
                  exit={{ opacity: 0, height: 0, transition: { duration: 0.3 } }}
                  className="text-sm"
                  style={{ overflow: 'hidden' }}
                >
                  {description}
                </motion.div>
              )}
            </div>
          </div>
          <button
            onClick={() =>
              purchase({ keyname, name, introduction, description, price, mode })
            }
            className="ml-auto px-8 border-l-2 border-dotted"
          >
            <ShoppingBagIcon width={16} height={16} />
          </button>
        </motion.div>
      </AnimatePresence>
    </>
  );
};

export default Item;
