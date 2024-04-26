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
    // <>
    //   <section className="w-full flex flex-col gap-[3rem]">
    //     {habitTotalData.map((habitYearData, index) => {
    //       return (
    //         <section
    //           key={`year-${index}`}
    //           className="w-full flex flex-col gap-[1rem]"
    //         >
    //           <label className="custom-semibold-text text-custom-black">
    //             {`${habitYearData.year}년`}
    //           </label>

    //           <ul className="w-full grid grid-cols-3">
    //             {habitYearData.habitList.map((habit, index) => {
    //               return (
    //                 <li key={`habit-${index}`}>
    //                   <motion.div
    //                     key={habit.habitTrackerId}
    //                     layoutId={`${habit.habitTrackerId}`}
    //                     className="cursor-pointer grayscale bg-custom-white rounded-[2rem]"
    //                     onClick={() => {
    //                       setHabitId(habit.habitTrackerId);
    //                     }}
    //                   >
    //                     <motion.p className="absolute bottom-0 right-0 custom-semibold-text text-custom-black">
    //                       {`${habit.succeededTime.getMonth() + 1}월 ${habit.succeededTime.getDate()}일`}
    //                     </motion.p>

    //                     <Image
    //                       src={'/images/icon-clock.png'}
    //                       width={100}
    //                       height={100}
    //                       alt={'clock'}
    //                       className="size-full"
    //                     ></Image>
    //                   </motion.div>
    //                 </li>
    //               );
    //             })}
    //           </ul>
    //         </section>
    //       );
    //     })}
    //   </section>

    //   {habitId && (
    //     <motion.div className="absolute size-full top-0 left-0">
    //       <AnimatePresence>
    //         {habitId && (
    //           <motion.div
    //             variants={OverlayVariants}
    //             initial="initial"
    //             animate="animate"
    //             exit="exit"
    //             className="size-full flex justify-center items-center"
    //             onClick={() => setHabitId(null)}
    //           >
    //             {
    //               <motion.div
    //                 className="w-5/6 h-5/6 p-[1rem] bg-custom-white rounded-[2rem] shadow-md absolute flex items-center justify-center"
    //                 layoutId={`${habitId}`}
    //               >
    //                 <motion.p className="text-[1.4rem] font-medium text-custom-black">
    //                   운동 갔다 왔다!!!! 오늘 한 운동은 스탠딩 숄더 프레스,
    //                   사이드 레터럴 레이즈, 암풀 다운이다...
    //                 </motion.p>
    //                 <Image
    //                   src={'/images/icon-clock.png'}
    //                   width={100}
    //                   height={100}
    //                   alt={'clock'}
    //                   className="object-cover"
    //                 ></Image>
    //               </motion.div>
    //             }
    //           </motion.div>
    //         )}
    //       </AnimatePresence>
    //     </motion.div>
    //   )}
    // </>

    <motion.div className="w-full h-full flex justify-start items-center">
      <section className="w-full flex flex-col gap-[3rem]">
        {habitTotalData.map((habitYearData, index) => {
          return (
            <section key={`year-${index}`} className="flex flex-col gap-[1rem]">
              <label className="custom-semibold-text text-custom-black">
                {`${habitYearData.year}년`}
              </label>

              <div className="grid grid-cols-3">
                {habitYearData.habitList.map((habit) => {
                  return (
                    <motion.div
                      key={habit.habitTrackerId}
                      layoutId={`${habit.habitTrackerId}`}
                      className="cursor-pointer grayscale bg-custom-white"
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
            className="size-full absolute flex justify-center items-center"
            onClick={() => setHabitId(null)}
            variants={OverlayVariants}
            initial="initial"
            animate="animate"
            exit="exit"
          >
            <motion.div
              className="w-1/2 h-1/2 bg-custom-white rounded-[2rem]"
              layoutId={`${habitId}`}
            />
          </motion.div>
        )}
      </AnimatePresence>
    </motion.div>
  );
};
