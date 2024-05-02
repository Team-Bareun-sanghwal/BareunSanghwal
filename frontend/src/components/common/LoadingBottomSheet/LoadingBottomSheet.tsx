'use client';

import { motion } from 'framer-motion';
import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-lego.json';
const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

interface ILoadingBottomSheetProps {
  title: string;
  open: boolean;
}

const container = {
  show: { y: 0, opacity: 1 },
  hidden: { y: '100%', opacity: 0 },
};

export const LoadingBottomSheet = ({
  title,
  open,
}: ILoadingBottomSheetProps) => {
  return (
    <>
      {open && (
        <div className="absolute top-0 left-0 size-full bg-custom-black-with-opacity"></div>
      )}

      <motion.section
        variants={container}
        initial={'hidden'}
        animate={open ? 'show' : 'hidden'}
        transition={{
          type: 'spring',
          mass: 0.5,
          damping: 40,
          stiffness: 400,
        }}
        className="fixed bottom-0 left-0 w-full min-w-[32rem] min-h-[24rem] p-[1rem] rounded-t-[1rem] bg-custom-white overflow-hidden flex flex-col"
      >
        <div className="p-[2rem] grow relative flex flex-col gap-[1rem] items-center">
          <span className="custom-bold-text text-pretty">{title}</span>

          <LottieBox
            loop
            animationData={lottieJson}
            play
            className="w-[15rem] h-[15rem]"
          />
        </div>
      </motion.section>
    </>
  );
};
