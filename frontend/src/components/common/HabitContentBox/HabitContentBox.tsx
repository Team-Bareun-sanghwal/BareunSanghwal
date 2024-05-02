'use client';

import Image from 'next/image';
import { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';

interface IHabitData {
  habitTrackerId: number;
  content: string;
  image: string;
  day: number;
  createdAt: Date;
  succeededTime: Date;
}

interface IHabitDataPerYear {
  year: number;
  habitList: IHabitData[];
}

interface IHabitContentBoxProps {
  habitTotalData: IHabitDataPerYear[];
}

const OverlayVariants = {
  initial: { backgroundColor: 'rgba(0, 0, 0, 0)' },
  animate: { backgroundColor: 'rgba(0, 0, 0, 0.6)' },
  exit: { backgroundColor: 'rgba(0, 0, 0, 0)' },
};

export const HabitContentBox = ({ habitTotalData }: IHabitContentBoxProps) => {
  const [habitId, setHabitId] = useState<number | null>(null);

  console.log(habitId);

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
                {habitYearData.habitList.map((habit) => {
                  return (
                    <motion.div
                      key={habit.habitTrackerId}
                      layoutId={`${habit.habitTrackerId}`}
                      className="cursor-pointer grayscale h-[11rem]"
                      onClick={() => {
                        setHabitId(habit.habitTrackerId);
                      }}
                    >
                      <motion.p className="absolute bottom-[0.5rem] right-[0.5rem] custom-semibold-text text-custom-black">
                        {`${habit.succeededTime.getMonth() + 1}월 ${habit.succeededTime.getDate()}일`}
                      </motion.p>

                      <Image
                        src={'/images/icon-clock.png'}
                        width={100}
                        height={100}
                        alt={'clock'}
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
            className="absolute top-0 left-0 size-full bg-custom-black-with-opacity flex justify-center items-center"
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
                src={'/images/icon-clock.png'}
                width={150}
                height={150}
                alt={'clock'}
                className="object-cover"
              ></Image>

              <motion.p className="custom-medium-text text-custom-black">
                운동 갔다 왔다!!!! 오늘 한 운동은 스탠딩 숄더 프레스, 사이드
                레터럴 레이즈, 암풀 다운이다...
              </motion.p>
              <motion.span className="custom-light-text">
                2024년 3월 23일
              </motion.span>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </motion.div>
  );
};
