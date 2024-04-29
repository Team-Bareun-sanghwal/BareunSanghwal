'use client';

import { useState } from 'react';
import { GuideText } from '../GuideText/GuideText';

export const TextAreaBox = () => {
  const [textLength, setTextLength] = useState<number>(0);
  const [outlineColor, setOutlineColor] = useState<string>(
    'outline-custom-green',
  );

  return (
    <section className="w-full flex flex-col gap-[0.5rem]">
      <label className="custom-semibold-text text-custom-black">
        해빗에 대한 내용을 작성해주세요.
      </label>

      <GuideText text="작성한 내용은 추후에 목록에서 확인할 수 있어요." />

      <textarea
        className={`${outlineColor} h-[25rem] p-[1rem] pb-[2rem] text-custom-black rounded-[1rem] resize-none custom-semibold-text bg-custom-light-gray`}
        placeholder="내용을 작성해주세요."
        maxLength={100}
        onChange={(event) => {
          if (event.target.value.length > 100) {
            setOutlineColor('outline-custom-error');
          } else {
            setOutlineColor('outline-custom-green');
          }
          setTextLength(event.target.value.length);
        }}
      ></textarea>

      <span className="block self-end text-custom-black custom-semibold-text">
        {textLength}/100
      </span>
    </section>
  );
};
