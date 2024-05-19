'use client';

import { useState } from 'react';
import EmojiPicker from 'emoji-picker-react';
import { EmojiClickData } from 'emoji-picker-react';
import { GuideText } from '../GuideText/GuideText';

export const Picker = ({
  label,
  selectedEmoji,
  setSelectedEmoji,
}: {
  label: string;
  selectedEmoji: string | null;
  setSelectedEmoji: (emoji: string) => void;
}) => {
  const [emojiOpen, setEmojiOpen] = useState(false);

  const Click = (data: EmojiClickData) => {
    setSelectedEmoji(data.emoji);
    setEmojiOpen(false);
  };

  const toggleEmoji = () => {
    setEmojiOpen(true);
  };

  return (
    <section className="w-full flex flex-col gap-[0.5rem]">
      <label className="custom-semibold-text text-custom-matcha">{label}</label>

      <GuideText text="해빗을 대표하는 이미지를 설정해주세요" />

      <div className="flex w-full relative">
        <div
          className="w-[5rem] h-[5rem] border-[0.1rem] border-custom-medium-gray bg-custom-white rounded-full flex items-center justify-center text-4xl select-none m-1"
          onClick={toggleEmoji}
        >
          {selectedEmoji}
        </div>
        {emojiOpen && (
          <div className="absolute bottom-0 right-0 z-10">
            <EmojiPicker
              onEmojiClick={Click}
              height={400}
              width={280}
              autoFocusSearch={true}
            />
          </div>
        )}
      </div>
    </section>
  );
};
