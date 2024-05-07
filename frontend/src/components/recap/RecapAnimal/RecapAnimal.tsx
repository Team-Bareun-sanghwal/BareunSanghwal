import dynamic from 'next/dynamic';
import lottieOwl from '@/../public/lotties/lottie-owl.json';
import lottieCat from '@/../public/lotties/lottie-cat.json';
import lottieDog from '@/../public/lotties/lottie-dog.json';
import lottieBlueBird from '@/../public/lotties/lottie-blue-bird.json';
import { GradientBar } from '../GradientBar/GradientBar';

interface IPropType {
  mostSubmitTime: string;
}

const LottieBox = dynamic(() => import('react-lottie-player'), { ssr: false });

const animalObj = new Map([
  [
    'DAWN',
    {
      pre: '깊은 새벽에 많이 달성하는 ',
      animal: '부엉이',
      post: '예요!',
      src: lottieOwl,
      background: 'bg-[#efe49a]',
    },
  ],
  [
    'MORNING',
    {
      pre: '이른 아침에 많이 달성하는 ',
      animal: '꾀꼬리',
      post: '예요!',
      src: lottieBlueBird,
      background: 'bg-[#c3eeff]',
    },
  ],
  [
    'EVENING',
    {
      pre: '활기찬 오후에 많이 달성하는 ',
      animal: '강아지',
      post: '예요!',
      src: lottieDog,
      background: 'bg-[#c8eaa6]',
    },
  ],
  [
    'NIGHT',
    {
      pre: '늦은 밤에 많이 달성하는 ',
      animal: '고양이',
      post: '예요!',
      src: lottieCat,
      background: 'bg-[#d96391]',
    },
  ],
]);

export const RecapAnimal = ({ mostSubmitTime }: IPropType) => {
  return (
    <div className="w-full flex flex-col h-[50rem] items-center justify-between">
      <div className="w-[36rem] h-full flex justify-center items-center">
        <div
          className={`${animalObj.get(mostSubmitTime)?.background} w-[34rem] h-[34rem] flex items-center border-[#392e27] border-[2.5rem] border-solid `}
        >
          <LottieBox
            loop
            animationData={animalObj.get(mostSubmitTime)?.src}
            play
            className="w-full"
          />
        </div>
      </div>
      <div className="w-full">
        <GradientBar
          textFront={`${animalObj.get(mostSubmitTime)?.pre}`}
          textMiddle={`${animalObj.get(mostSubmitTime)?.animal}`}
          textBack={`${animalObj.get(mostSubmitTime)?.post}`}
        />
      </div>
    </div>
  );
};
