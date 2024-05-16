import { IDayInfo } from '@/app/mock';
import { motion, AnimatePresence } from 'framer-motion';
import { getToday } from '../util';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';
import { useOverlay } from '@/hooks/use-overlay';
type TAchieveType = 'NOT_EXISTED' | 'ACHIEVE' | 'NOT_ACHIEVE' | 'RECOVERY';
interface IRecoveryStreakrProps {
  selected: boolean;
  dayNumber: number;
  achieveType: TAchieveType;
  setSelected: React.Dispatch<React.SetStateAction<number>>;
}

export const RecoveryStreak = ({
  selected,
  dayNumber,
  achieveType,
  setSelected,
}: IRecoveryStreakrProps) => {
  const overlay = useOverlay();
  const commonClass =
    'flex text-white text-2xl w-16 h-16 rounded-md items-center justify-center';
  const DISABLED =
    'flex text-white text-2xl w-16 h-16 rounded-md items-center justify-center';
  // const selectedClass = 'flex text-white bg-gray-600 text-2xl w-16 h-16 rounded-md items-center justify-center border-2 border-black'
  const buttonClick = () => {
    if (achieveType === 'NOT_ACHIEVE') {
      if (selected) setSelected(0);
      else setSelected(dayNumber);
    } else {
      overlay.open(({ isOpen, close }) => (
        <AlertBox
          label={'스트릭을 복구할 수 없는 날짜입니다'}
          mode="WARNING"
          open={isOpen}
        />
      ));
      setTimeout(() => overlay.close(), 1000);
    }
  };
  return (
    <>
      <motion.button
        onClick={() => buttonClick()}
        className={achieveType === 'NOT_ACHIEVE' ? commonClass : DISABLED}
        animate={{
          backgroundColor:
            achieveType == 'NOT_ACHIEVE'
              ? selected
                ? dayNumber !== parseInt(getToday(false))
                  ? '#15481b'
                  : '#acd4be'
                : '#acd4be'
              : '#c9c9c9',
          scale: selected ? 1.05 : 1,
        }}
      >
        {dayNumber}
      </motion.button>
    </>
  );
};
