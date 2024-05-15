'use client';

import Image from 'next/image';
import {Button} from '../../common/Button/Button';
import { motion } from 'framer-motion';
import { $Fetch } from '@/apis';
import { useEffect, useState } from 'react';
import { getYear, getMonth } from '@/components/calendar/util';
import { setDayInfo } from '@/app/mock';
import { Calender } from '@/components/calendar/Calender/Calender';
import { IDayInfo } from '@/app/mock';
interface IBottomSheetProps {
  title: string;
  description: string;
  open: boolean;
  onClose?: () => void;
  onConfirm?: () => void;
  children?: React.ReactNode;
}

const container = {
  show: { y: 0, opacity: 1 },
  hidden: { y: '100%', opacity: 0 },
};
const getData = async()=>{
  const result = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/streaks/${getYear()}-${getMonth(true)}`,
    cache: 'no-cache',
  })
  if ( result.status === 200) {
    return result.data;
  }
}
export const Recovery = ({
  title,
  description,
  open,
  onClose,
  onConfirm,
  children
}: IBottomSheetProps) => {
  const [days, setDays] = useState<IDayInfo[] | never[]>([]);

  const getDayInfo = async()=>{
    console.log('hi!')
    const daysResponse = await getData();
    console.log(daysResponse.dayInfo)
    setDays(daysResponse.dayInfo);
  }
  useEffect(() => {
    getDayInfo();
  },[])

  return (
    <>
      {open && (
        <div className="absolute top-0 left-0 w-full h-[200vh] bg-custom-black-with-opacity"></div>
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
        className="fixed bottom-0 left-0 w-full min-w-[32rem] min-h-[48rem] p-[1rem] rounded-t-[1rem] bg-custom-white overflow-hidden flex flex-col"
      >
        <div className="grow relative">
          <div className="w-2/3 pl-[1rem] py-[1rem] flex flex-col gap-[1rem]">
            <span className="custom-semibold-text text-pretty">{title}</span>
            {days? days.length ?<Calender
              dayInfo={setDayInfo(days)}
              memberHabitList={[]}
              dayOfWeekFirst={0}
              themeColor="dippindots"
              proportion={0}
              longestStreak={0}
              year={parseInt(getYear())}
              month={parseInt(getMonth(false))}
              recovery={true}
            />: <div>데이터가 없습니다.</div>:<div>로딩중...</div>}
            <span className="custom-medium-text text-pretty">
              {description}
            </span>
          </div>
          <span className="flex flex-col w-full content-center custom-regular-text text-pretty">
            {children}
          </span>
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
