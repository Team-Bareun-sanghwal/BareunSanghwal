'use client';

import Image from 'next/image';
import { useState } from 'react';
import Confetti from 'react-confetti';

interface IHabitType {
  mainHabit: string;
}

export const Trophy = ({ mainHabit }: IHabitType) => {
  const [isLoading, setIsLoading] = useState(true);

  setTimeout(() => {
    setIsLoading(false);
  }, 3000);

  return (
    <div className="w-full h-[50rem] flex flex-col items-center justify-center">
      {isLoading ? (
        <div className="spotlight" />
      ) : (
        <>
          <Confetti
            width={360}
            height={500}
            gravity={0.1}
            numberOfPieces={200}
            opacity={0.5}
            recycle={false}
            initialVelocityY={-10}
            drawShape={(ctx) => {
              ctx.beginPath();
              ctx.moveTo(0, 0); // 시작점
              ctx.lineTo(5, 0); // 오른쪽 위
              ctx.lineTo(5, 5); // 오른쪽 아래
              ctx.lineTo(0, 5); // 왼쪽 아래
              ctx.lineTo(0, 0); // 다시 시작점으로
              ctx.stroke(); // 사각형 그리기
              ctx.fill(); // 색 채우기
              ctx.closePath(); // 그림 닫기
            }}
          />
          <Image
            src="/images/icon-trophy.png"
            alt="trophy"
            width={300}
            height={300}
          />
          <p className="text-custom-yellow-green custom-bold-text text-center">
            {mainHabit} !
          </p>
        </>
      )}
    </div>
  );
};
