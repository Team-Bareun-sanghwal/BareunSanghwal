'use client';

import dynamic from 'next/dynamic';
import lottieJson from '@/../public/lotties/lottie-lego.json';
import { useRouter } from 'next/navigation';

const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

interface ErrorPageProps {
  errorTitle: string;
  errorDescription: string;
  buttonText: string;
  nextPage: string;
}

export const ErrorPage = ({
  errorTitle,
  errorDescription,
  buttonText,
  nextPage,
}: ErrorPageProps) => {
  const router = useRouter();

  return (
    <section className="w-full flex flex-col gap-[1rem] justify-center items-center">
      <LottieBox
        loop
        animationData={lottieJson}
        play
        className="w-[10rem] h-[10rem]"
      />

      <span className="block custom-semibold-text">{errorTitle}</span>
      <p className="text-custom-dark-gray custom-medium-text -m-[0.5rem]">
        {errorDescription}
      </p>
      <button
        className="w-fit custom-medium-text px-[1rem] py-[0.5rem] text-custom-white bg-custom-matcha rounded-[1rem]"
        onClick={() => router.push(nextPage)}
      >
        {buttonText}
      </button>
    </section>
  );
};
