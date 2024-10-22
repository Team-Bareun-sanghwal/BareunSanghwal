'use client';

import { useRouter } from 'next/navigation';
import { PlusIcon } from '@heroicons/react/24/solid';
import { motion, useAnimate } from 'framer-motion';
import { useOverlay } from '@/hooks/use-overlay';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';

export const HabitBtn = ({
  memberHabitId,
  alias,
  icon,
  habitId,
  shortcut,
  add,
  succeededTime,
  today,
  setHabitId,
  limit,
}: {
  memberHabitId: number;
  alias: string;
  icon: string;
  habitId?: number;
  shortcut?: boolean;
  add?: boolean;
  succeededTime?: string;
  today?: boolean;
  setHabitId?: React.Dispatch<React.SetStateAction<number>>;
  limit?: boolean;
}) => {
  const [scope, animate] = useAnimate();
  const router = useRouter();
  const overlay = useOverlay();
  const onClickHabit = () => {
    animate([
      ['button', { scale: 1.1 }, { duration: 0.1 }],
      ['button', { scale: 1 }, { duration: 0.1 }],
    ]);

    if (memberHabitId && setHabitId) {
      setHabitId(memberHabitId);
    }
    if (!add) {
      if (shortcut) {
        if (succeededTime === null)
          router.push(`/habit/write/${memberHabitId}`);
        else {
          overlay.open(({ isOpen }) => (
            <AlertBox
              label="이미 오늘 기록한 습관이에요!"
              mode="WARNING"
              open={isOpen}
            />
          ));
          setTimeout(() => overlay.close(), 1000);
        }
      } else {
        if (memberHabitId === -1) {
          console.log('go to main');
          router.replace(`/main`);
        } else if (memberHabitId == habitId) {
          router.push(`/main`);
        } else {
          router.push(`/main`);
        }
      }
    } else {
      if (limit) {
        overlay.open(({ isOpen }) => (
          <AlertBox
            label="더 이상 습관을 추가할 수 없어요!"
            mode="WARNING"
            open={isOpen}
          />
        ));
        setTimeout(() => overlay.close(), 1000);
      } else router.push('/habit/register');
    }
  };

  return (
    <>
      <div className="flex flex-col items-center justify-center" ref={scope}>
        <motion.button
          onClick={onClickHabit}
          className={
            shortcut
              ? add
                ? 'flex bg-gradient-to-l from-custom-pink to-custom-sky text-4xl w-24 h-24 rounded-full justify-center items-center m-[0.5rem]'
                : today && succeededTime
                  ? 'relative bg-custom-sky-pastel text-4xl w-24 h-24 rounded-full custom-gradient-border m-[0.5rem]'
                  : 'relative bg-custom-sky-pastel text-4xl w-24 h-24 rounded-full border-[0.3rem] border-gray-300 m-[0.5rem]'
              : memberHabitId == habitId
                ? 'relative bg-gray-300 text-2xl w-16 h-16 rounded-full '
                : 'relative bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full'
          }
        >
          {add ? <PlusIcon className="w-12 h-12 text-custom-white" /> : icon}
        </motion.button>

        <p
          className={
            shortcut
              ? 'text-s mt-1 text-center w-24 truncate ...'
              : 'text-xs text-center w-16 truncate ...'
          }
        >
          {alias}
        </p>
      </div>
    </>
  );
};
