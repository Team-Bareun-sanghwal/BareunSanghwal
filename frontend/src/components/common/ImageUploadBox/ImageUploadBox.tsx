'use client';

import Image from 'next/image';
import { GuideText } from '../GuideText/GuideText';
import { PlusIcon, ArrowPathIcon, XMarkIcon } from '@heroicons/react/24/solid';

export const ImageUploadBox = ({
  image,
  setImage,
}: {
  image: File | null;
  setImage: (image: File | null) => void;
}) => {
  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const selectedImageFile = event.target.files && event.target.files[0];
    if (selectedImageFile) setImage(selectedImageFile);
  };

  const handleImageDelete = () => {
    setImage(null);
  };

  return (
    <section className="w-full flex flex-col gap-[0.5rem]">
      <span className="custom-semibold-text text-custom-black">
        해빗에 대한 이미지를 추가해주세요.(선택)
      </span>

      <GuideText text="해빗 이미지는 추후에 목록이나 리캡에서 확인할 수 있어요!" />

      <div className="flex flex-col gap-[0.5rem]">
        {image ? (
          <div className="w-full h-[25rem] border-dashed border-[0.1rem] rounded-[1rem] border-custom-dark-gray bg-custom-light-gray flex justify-center items-center">
            <Image
              src={URL.createObjectURL(image)}
              width={200}
              height={200}
              alt="업로드 이미지"
              className="bg-contain"
            />
          </div>
        ) : (
          <label
            htmlFor="file"
            className="w-full h-[25rem] border-dashed border-[0.1rem] rounded-[1rem] border-custom-dark-gray bg-custom-light-gray hover:bg-custom-medium-gray flex justify-center items-center cursor-pointer"
          >
            <PlusIcon className="w-[3.6rem] h-[3.6rem] text-custom-dark-gray" />
            <input
              type="file"
              id="file"
              accept="image/*"
              className="hidden"
              onChange={handleImageUpload}
            ></input>
          </label>
        )}
        {image && (
          <ul className="self-end custom-semibold-text flex gap-[1rem]">
            <li>
              <label
                htmlFor="updateFile"
                className="flex gap-[0.2rem] items-center justify-center cursor-pointer text-custom-dark-gray"
              >
                <ArrowPathIcon className="w-[1.8rem] h-[1.8rem]" />
                <span>수정하기</span>
                <input
                  type="file"
                  id="updateFile"
                  accept="image/*"
                  className="hidden"
                  onChange={handleImageUpload}
                ></input>
              </label>
            </li>
            <li>
              <button
                onClick={handleImageDelete}
                className="flex gap-[0.2rem] items-center justify-center cursor-pointer text-custom-error"
              >
                <XMarkIcon className="w-[1.8rem] h-[1.8rem]" />
                <span>삭제하기</span>
              </button>
            </li>
          </ul>
        )}
      </div>
    </section>
  );
};
