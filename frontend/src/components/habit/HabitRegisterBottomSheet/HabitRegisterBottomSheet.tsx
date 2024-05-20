'use client';

import { Button } from '../../common/Button/Button';
import { motion } from 'framer-motion';
import { ReactNode } from 'react';

interface IHabitRegisterBottomSheetProps {
  element: ReactNode;
  open: boolean;
  onClose?: () => void;
  onConfirm?: () => void;
}

const container = {
  show: { y: 0, opacity: 1 },
  hidden: { y: '100%', opacity: 0 },
};

export const HabitRegisterBottomSheet = ({
  element,
  open,
  onClose,
  onConfirm,
}: IHabitRegisterBottomSheetProps) => {
  return (
    <>
      {open && (
        <div className="absolute top-0 left-0 w-full h-[150vh] bg-custom-black-with-opacity"></div>
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
        className="fixed bottom-0 left-0 w-full min-w-[32rem] p-[1rem] rounded-t-[1rem] bg-custom-white overflow-hidden flex flex-col"
      >
        <div className="w-full p-[1rem] flex flex-col gap-[1rem]">
          <span className="custom-semibold-text text-pretty">
            해빗을 등록하시겠어요?
          </span>

          {element}
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
