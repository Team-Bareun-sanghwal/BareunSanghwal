import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';

const OverlayVariants = {
  initial: { backgroundColor: 'rgba(0, 0, 0, 0)' },
  animate: { backgroundColor: 'rgba(0, 0, 0, 0.5)' },
  exit: { backgroundColor: 'rgba(0, 0, 0, 0)' },
};

export const Button = () => {
  const [id, setId] = useState<null | string>(null);
  return (
    <motion.div className="w-[50rem] h-full flex justify-around items-center">
      <div className="grid grid-cols-3 w-1/2 gap-[1rem]">
        {['1', '2', '3', '4'].map((n) => (
          // layoutId는 string이어야 한다.
          <motion.div
            className="h-[20rem] bg-custom-white rounded-[4rem] shadow-md"
            onClick={() => setId(n)}
            key={n}
            layoutId={n}
          />
        ))}
      </div>
      <AnimatePresence>
        {id && (
          <motion.div
            className="size-full absolute flex justify-center items-center"
            onClick={() => setId(null)}
            variants={OverlayVariants}
            initial="initial"
            animate="animate"
            exit="exit"
          >
            {/* 모달 layoutId */}
            <motion.div
              className="h-[20rem] bg-custom-white rounded-[4rem] shadow-md"
              layoutId={id}
              style={{ width: 400, height: 200 }}
            />
          </motion.div>
        )}
      </AnimatePresence>
    </motion.div>
  );
};
