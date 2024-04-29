'use client';

import { motion } from 'framer-motion';
import {
  CheckBadgeIcon,
  ExclamationCircleIcon,
  XCircleIcon,
} from '@heroicons/react/24/solid';

interface IAlertBoxProps {
  mode: 'SUCCESS' | 'WARNING' | 'ERROR';
  label: string;
  open: boolean;
}

const container = {
  show: { y: '-150%', opacity: 1 },
  hidden: { y: '100%', opacity: 0 },
};

export const AlertBox = ({ mode, label, open }: IAlertBoxProps) => {
  return (
    <motion.div
      variants={container}
      initial={'hidden'}
      animate={open ? 'show' : 'hidden'}
      transition={{
        type: 'spring',
        mass: 0.5,
        damping: 40,
        stiffness: 400,
      }}
      className="fixed w-full bottom-0 flex justify-center items-center"
    >
      <div className="px-[1.5rem] py-[1rem] flex items-center gap-[0.8rem] bg-custom-black-with-opacity rounded-[2rem]">
        {mode === 'SUCCESS' ? (
          <CheckBadgeIcon className="w-[2.4rem] h-[2.4rem] text-custom-success" />
        ) : mode === 'WARNING' ? (
          <ExclamationCircleIcon className="w-[2.4rem] h-[2.4rem] text-custom-warning" />
        ) : (
          <XCircleIcon className="w-[2.4rem] h-[2.4rem] text-custom-error" />
        )}

        <p className="max-w-[24rem] text-custom-white text-[1.4rem] font-bold">
          {label}
        </p>
      </div>
    </motion.div>
  );
};
