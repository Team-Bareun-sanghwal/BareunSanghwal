'use client';

import { InputBox, ScrollDatePicker, SelectBox } from '@/components';
import { TinyButton } from '@/components/common/TinyButton/TinyButton';
import { ReactNode, useState } from 'react';

interface IPropType {
  title: string;
  prevData: string;
}

export const InfoModify = ({ title, prevData }: IPropType) => {
  const [isOpen, setIsOpen] = useState(false);
  const [value, setValue] = useState(prevData);

  const keyValueMap = new Map([
    ['M', '남자'],
    ['F', '여자'],
    ['N', '말하고 싶지 않아요'],
    ['STUDENT', '학생'],
    ['EMPLOYEE', '회사원'],
    ['HOUSEWIFE', '주부'],
    ['JOB_SEEKER', '취준생'],
    ['SELF_EMPLOYED', '자영업'],
  ]);

  const toggleIsOpen = () => {
    setIsOpen(!isOpen);
  };

  const saveData = () => {
    // api 통신 추가

    toggleIsOpen();
  };

  const modifyContent =
    title === '닉네임' ? (
      <InputBox
        mode="NICKNAME"
        isLabel={false}
        defaultValue={value}
        setDefaultValue={setValue}
      />
    ) : title === '성별' ? (
      <SelectBox
        options={[
          { key: 'M', value: '남자' },
          { key: 'F', value: '여자' },
          { key: 'N', value: '말하고 싶지 않아요' },
        ]}
        defaultValue={value}
        setDefaultValue={setValue}
      />
    ) : title === '직업' ? (
      <SelectBox
        options={[
          { key: 'STUDENT', value: '학생' },
          { key: 'EMPLOYEE', value: '회사원' },
          { key: 'HOUSEWIFE', value: '주부' },
          { key: 'JOB_SEEKER', value: '취준생' },
          { key: 'SELF_EMPLOYED', value: '자영업' },
        ]}
        defaultValue={value}
        setDefaultValue={setValue}
      />
    ) : (
      <ScrollDatePicker birthDay={value} setBirthDay={setValue} />
    );

  return (
    <div className="w-full flex flex-col items-center">
      <div className="w-full flex justify-between">
        <div className="flex items-center">
          <p className="w-[6rem] text-left custom-semibold-text text-custom-matcha">
            {title}
          </p>
          <p className="custom-medium-text text-custom-black">
            {['성별', '직업'].includes(title) ? keyValueMap.get(value) : value}
          </p>
        </div>

        {isOpen ? (
          <TinyButton mode="SAVE" label="저장" onClick={saveData} />
        ) : (
          <TinyButton mode="MODIFY" label="수정" onClick={toggleIsOpen} />
        )}
      </div>
      {isOpen ? <div className="w-full mt-[3rem]">{modifyContent}</div> : null}
    </div>
  );
};
