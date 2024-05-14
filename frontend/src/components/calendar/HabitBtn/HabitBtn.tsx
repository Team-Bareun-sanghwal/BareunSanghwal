'use client';
import { CheckIcon } from '@heroicons/react/24/outline';
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
}: {
  memberHabitId: number;
  alias: string;
  icon: string;
  habitId?: number;
  shortcut?: boolean;
  add?: boolean;
  succeededTime?: string;
  today?: boolean;
}) => {
  const router = useRouter();
  const overlay = useOverlay();
  const [scope, animate] = useAnimate();

  const onClickHabit = () => {
    animate([
      ['button', { scale: 1.1 }, { duration: 0.1 }],
      ['button', { scale: 1 }, { duration: 0.1 }],
    ]);
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
        }
      } else {
        if (memberHabitId == habitId) {
          router.push(`/main/2024/5`);
        } else {
          router.push(`/main/2024/5/${memberHabitId}`);
        }
      }
    } else {
      router.push('/habit/register');
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
                ? 'flex  bg-custom-sky-pastel text-4xl w-24 h-24 rounded-full justify-center items-center'
                : 'relative bg-custom-sky-pastel text-4xl w-24 h-24 rounded-full'
              : memberHabitId == habitId
                ? 'relative bg-gray-300 text-2xl w-16 h-16 rounded-full '
                : 'relative bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full'
          }
        >
          {add ? <PlusIcon className="w-12 h-12 text-gray-300" /> : icon}
          {today &&
            (succeededTime ? (
              <div className="absolute bottom-0 right-0  text-white bg-green-600 p-2 rounded-full text-xs">
                <CheckIcon className="w-4 h-4" />
              </div>
            ) : (
              <div className="absolute bottom-0 right-0 bg-gray-400 p-2 rounded-full text-white text-xs">
                <CheckIcon className="w-4 h-4" />
              </div>
            ))}
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
