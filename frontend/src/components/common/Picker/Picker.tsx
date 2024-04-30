'use client';

import { useState } from 'react';
import EmojiPicker from 'emoji-picker-react';
import { EmojiClickData } from 'emoji-picker-react';

export const Picker = ({ label }: { label: string }) => {
  const [selectedEmoji, setSelectedEmoji] = useState<string | null>(null);
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
      <label className="custom-semibold-text text-custom-black">{label}</label>
      <div className="flex w-full relative">
        <div
          className="w-[5rem] h-[5rem] border-2 border-custom-dark-gray bg-custom-white rounded-full flex items-center justify-center text-4xl select-none m-1"
          onClick={toggleEmoji}
        >
          {!selectedEmoji && '😀'}
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
