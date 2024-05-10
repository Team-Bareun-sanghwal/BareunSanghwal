import Image from 'next/image';

export const BlockStack = () => {
  return (
    <div className="relative flex flex-col h-[40rem] min-w-[23rem]">
      <Image
        src="/images/icon-block1.png"
        alt="block1"
        width={230}
        height={230}
        className="absolute z-30 loading-block-red"
      />
      <Image
        src="/images/icon-block2.png"
        alt="block2"
        width={230}
        height={230}
        className="absolute z-20 loading-block-yellow"
      />
      <Image
        src="/images/icon-block3.png"
        alt="block3"
        width={230}
        height={230}
        className="absolute z-10 loading-block-blue"
      />
      <Image
        src="/images/icon-block4.png"
        alt="block4"
        width={230}
        height={230}
        className="absolute z-0 loading-block-white"
      />
    </div>
  );
};
