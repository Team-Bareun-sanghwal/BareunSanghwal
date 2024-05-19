'use client';
import { IDayInfo } from '@/app/mock';
import { RecoveryStreak } from '../RecoveryStreak/RecoveryStreak';
import React from 'react';

interface IRecoveryCalenderProps {
    days:IDayInfo[];
    selected: number;
    setSelected:React.Dispatch<React.SetStateAction<number>>;
}
export const RecoveryCalender = ({days, selected ,setSelected} : IRecoveryCalenderProps) => {

    return <>
        <div className="grid grid-cols-7 gap-4 p-1 m-2.5">
            {days.map((info, index) =>
                info.dayNumber<0 ?<div key={index}/>:
                <RecoveryStreak
                    key={index}
                    selected={selected===info.dayNumber}
                    achieveType={info.achieveType}
                    dayNumber={info.dayNumber}
                    setSelected={setSelected}/>
            )}
        </div>
    </>
}