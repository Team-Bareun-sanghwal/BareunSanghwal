'use client';

import Image from 'next/image';
import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { IWriteListData, IHabitTrackerData } from '@/app/habit/_types';

const OverlayVariants = {
  initial: { backgroundColor: 'rgba(0, 0, 0, 0)' },
  animate: { backgroundColor: 'rgba(0, 0, 0, 0.6)' },
  exit: { backgroundColor: 'rgba(0, 0, 0, 0)' },
};

export const HabitContentBox = ({
  habitTotalData,
}: {
  habitTotalData: IWriteListData[];
}) => {
  const [habitId, setHabitId] = useState<number | null>(null);
  const [habitYear, setHabitYear] = useState<number>(0);
  const [habitContent, setHabitContent] = useState<IHabitTrackerData>({
    habitTrackerId: 0,
    succeededTime: new Date(),
    content: '',
    image: '',
    period: '',
  });

  return (
    <motion.div className="w-full h-full flex justify-start items-center">
      <section className="w-full flex flex-col gap-[3rem]">
        {habitTotalData.map((habitYearData, index) => {
          return (
            <section key={`year-${index}`} className="flex flex-col gap-[1rem]">
              <label className="custom-semibold-text text-custom-black">
                {`${habitYearData.year}년`}
              </label>

              <div className="grid grid-cols-3 grid-flow-row-dense">
                {habitYearData.habitTrackerList.map((habit) => {
                  return (
                    <motion.div
                      key={habit.habitTrackerId}
                      layoutId={`${habit.habitTrackerId}`}
                      className="cursor-pointer grayscale h-[11rem]"
                      onClick={() => {
                        setHabitId(habit.habitTrackerId);
                        setHabitYear(habitYearData.year);
                        setHabitContent(habit);
                      }}
                    >
                      <motion.p className="absolute bottom-[0.5rem] right-[0.5rem] custom-semibold-text text-custom-black">
                        {habit.period}
                      </motion.p>

                      <Image
                        src={habit.image || '/images/icon-clock.png'}
                        width={100}
                        height={100}
                        alt={'해빗 이미지'}
                        className="size-full object-cover"
                      ></Image>
                    </motion.div>
                  );
                })}
              </div>
            </section>
          );
        })}
      </section>

      <AnimatePresence>
        {habitId && (
          <motion.div
            className="fixed top-0 left-0 size-full bg-custom-black-with-opacity flex justify-center items-center"
            onClick={() => setHabitId(null)}
            variants={OverlayVariants}
            initial="initial"
            animate="animate"
            exit="exit"
          >
            <motion.div
              className="w-4/5 h-fit bg-custom-white rounded-[2rem] p-[2rem] flex flex-col items-center gap-[1rem]"
              layoutId={`${habitId}`}
            >
              <Image
                src={habitContent.image || '/images/icon-clock.png'}
                width={150}
                height={150}
                alt={'해빗 이미지'}
                className="object-cover"
              ></Image>

              <motion.p className="custom-medium-text text-custom-black">
                {habitContent.content}
              </motion.p>
              <motion.span className="custom-light-text">
                {`${habitYear}년 ${habitContent.period}`}
              </motion.span>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </motion.div>
  );
};
