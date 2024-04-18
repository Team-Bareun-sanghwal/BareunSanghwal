import Image from 'next/image';

interface IGuideBoxProps {
  guideText: string;
  onClick?: () => void;
}

export const GuideBox = ({ guideText, ...props }: IGuideBoxProps) => {
  return (
    <section
      className={
        'w-[34rem] h-[6rem] p-[1rem] flex gap-[1rem] items-center justify-center rounded-[1rem] bg-custom-light-gray'
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
      <p className="custom-light-text">{guideText}</p>
    </section>
  );
};
