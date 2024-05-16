'use client';

import dynamic from 'next/dynamic';
const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });
import lottieJson from '@/../public/lotties/lottie-lego.json';

export default function GlobalError({
  error,
  reset,
}: {
  error: Error & { digest?: string };
  reset: () => void;
}) {
  return (
    <html>
      <body>
        <div className="size-full flex items-center justify-center">
          <section className="w-full flex flex-col gap-[1rem] justify-center items-center">
            <LottieBox
              loop
              animationData={lottieJson}
              play
              className="w-[10rem] h-[10rem]"
            />

            <span className="block custom-semibold-text">
              세션이 만료되었어요
            </span>
            <p className="text-custom-dark-gray custom-medium-text -m-[0.5rem]">
              어플리케이션을 다시 시작해주세요
            </p>
            <button
              className="w-fit custom-medium-text px-[1rem] py-[0.5rem] text-custom-white bg-custom-matcha rounded-[1rem]"
              onClick={() => window.location.replace('https://bareun.life')}
            >
              처음 화면으로 가기
            </button>
          </section>
        </div>
      </body>
    </html>
  );
}
