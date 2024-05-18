'use client';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';
import { GiftIcon, CheckCircleIcon } from '@heroicons/react/24/outline';
import { $Fetch } from '@/apis';
import { useOverlay } from '@/hooks/use-overlay';
import { motion, AnimatePresence } from 'framer-motion';
import { useState } from 'react';
import { useRouter } from 'next/navigation';
export const Harvest = ({ isHarvested }: { isHarvested: boolean }) => {
  const [harvested, setHarvested] = useState(isHarvested);
  const [isVisible, setIsVisible] = useState(true);
  const [msg, setMsg] = useState('');
  const overlay = useOverlay();
  const router = useRouter();
  const buttonText = harvested ? '오늘의 포인트를 이미 수확했어요!' : '';

  const getPoint = async () => {
    const response = await $Fetch({
      method: 'GET',
      url: `${process.env.NEXT_PUBLIC_BASE_URL}/members/tree/point`,
      cache: 'default',
    });
    return response;
  };

  const HandleAlertBox = (harvested: boolean, msg: string) => {
    if (harvested) {
      overlay.open(({ isOpen, close }) => (
        <AlertBox label={msg} mode="SUCCESS" open={isOpen} />
      ));
    } else {
      overlay.open(({ isOpen, close }) => (
        <AlertBox label={msg} mode="ERROR" open={isOpen} />
      ));
    }
    setTimeout(() => overlay.close(), 2000);
  };

  const handleClick = async () => {
    await getPoint()
      .then((res) => {
        if (res.status === 200) {
          const point = res.data.point;
          HandleAlertBox(true, `오늘의 포인트 ${point}점을 수확했어요!`);
          setHarvested(true);
          setTimeout(() => router.refresh(), 1000);
        } else if (res.status === 403) {
          HandleAlertBox(false, `이미 오늘의 포인트를 수령했어요...`);
        }
      })
      .catch((res) => {
        console.log(res.data.message);
        HandleAlertBox(false, `이미 오늘의 포인트를 수령했어요...`);
        setHarvested(true);
      });
  };
  return (
    <div className="absolute z-20 top-2 right-2 text-lg">
      <AnimatePresence>
        {isVisible && (
          <motion.button
            exit={{ x: 300, opacity: 0, transition: { duration: 0.5 } }}
            onAnimationComplete={() => {
              if (harvested) {
                setMsg('오늘의 포인트를 수확했어요!');
                setTimeout(() => setIsVisible(false), 2000);
              }
            }}
            onClick={() => handleClick()}
            initial={{ scale: 1 }}
            whileTap={{ scale: 0.95 }}
            animate={{
              backgroundColor: harvested ? '#f3f4f6' : '#f59e0b',
              width: harvested ? 'auto' : '4rem',
              padding: '1rem',
            }}
            className={
              harvested
                ? 'flex p-4 w-auto h-16 min-w-24 text-2xl items-center bg-white rounded-full'
                : 'flex p-4 w-16 h-16 text-2xl items-center bg-yellow-500 rounded-full content-center justify-center'
            }
          >
            <div className="">
              {harvested ? (
                <CheckCircleIcon className="w-8 h-8 mr-2" />
              ) : (
                <GiftIcon color="white" className="w-8 h-8" />
              )}
            </div>
            <motion.span className={harvested ? '' : 'text-white'}>
              {msg}
            </motion.span>
          </motion.button>
        )}
      </AnimatePresence>
    </div>
  );
};
