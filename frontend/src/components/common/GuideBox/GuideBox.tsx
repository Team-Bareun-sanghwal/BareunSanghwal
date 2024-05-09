import Image from 'next/image';
import { ReactElement } from 'react';

interface IGuideBoxProps {
  guideText: string | ReactElement;
  onClick?: () => void;
}

export const GuideBox = ({ guideText, ...props }: IGuideBoxProps) => {
  return (
    <section
      className={
        'w-full h-[6rem] p-[1rem] flex gap-[1rem] items-center justify-start rounded-[1rem] bg-custom-light-gray'
      }
      {...props}
    >
      <Image
        src={'/images/icon-clock.png'}
        width={40}
        height={40}
        alt={'clock'}
        className="w-[4rem] h-[4rem]"
      ></Image>
      {typeof guideText === 'string' ? (
        <p className="custom-light-text">{guideText}</p>
      ) : (
        { ...guideText }
      )}
    </section>
  );
};
