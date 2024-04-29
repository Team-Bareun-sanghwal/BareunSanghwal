'use client';

import Image from 'next/image';
import { Button } from '../Button/Button';
import { motion } from 'framer-motion';

interface IBottomSheetProps {
  title: string;
  description: string;
  mode: 'POSITIVE' | 'NEGATIVE' | 'RECOVERY' | 'NONE';
  open: boolean;
  onClose?: () => void;
  onConfirm?: () => void;
}

const container = {
  show: { y: 0, opacity: 1 },
  hidden: { y: '100%', opacity: 0 },
};

export const BottomSheet = ({
  title,
  description,
  mode,
  open,
  onClose,
  onConfirm,
}: IBottomSheetProps) => {
  const imageName =
    mode === 'POSITIVE'
      ? 'check'
      : mode === 'NEGATIVE'
        ? 'error'
        : mode === 'RECOVERY'
          ? 'lightning'
          : '';

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
        <div className="grow relative">
          <div className="w-2/3 pl-[1rem] py-[1rem] flex flex-col gap-[1rem]">
            <span className="custom-semibold-text text-pretty">{title}</span>
            <span className="custom-medium-text text-pretty">
              {description}
            </span>
          </div>

          {mode !== 'NONE' && (
            <Image
              priority={true}
              src={`/images/icon-${imageName}.png`}
              width={140}
              height={140}
              alt={'BottomSheet 이미지'}
              className="absolute -right-[5rem] bottom-0 w-[14rem] h-[14rem]"
            />
          )}
        </div>

        <div className="flex gap-[1rem] mt-[1rem]">
          {onClose && (
            <Button isActivated={false} label="취소" onClick={onClose} />
          )}
          {onConfirm && (
            <Button isActivated={true} label="확인" onClick={onConfirm} />
          )}
        </div>
      </motion.section>
    </>
  );
};
