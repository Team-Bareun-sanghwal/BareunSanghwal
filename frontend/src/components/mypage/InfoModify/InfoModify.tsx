'use client';

import { postMemberInfo } from '@/app/(member)/mypage/_apis/postMemberInfo';
import { InputBox, SelectBox } from '@/components';
import { DatePicker } from '@/components/common/DatePicker/DatePicker';
import { TinyButton } from '@/components/common/TinyButton/TinyButton';
import { useState } from 'react';
import { convertBirthday } from '@/components/common/Picker/utils';

interface IPropType {
  title: string;
  desc: string;
  prevData: string;
}

export default function InfoModify({ title, desc, prevData }: IPropType) {
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
    interface IDataType {
      [key: string]: string | null;
    }

    const data: IDataType = {
      nickname: null,
      birthDate: null,
      gender: null,
      job: null,
    };

    const key: keyof IDataType = title;
    data[key] = title === 'birthDate' ? convertBirthday(value) : value;

    postMemberInfo({ data });
    toggleIsOpen();
  };

  const modifyContent =
    desc === '닉네임' ? (
      <InputBox
        mode="NICKNAME"
        isLabel={false}
        defaultValue={value}
        setDefaultValue={setValue}
      />
    ) : desc === '성별' ? (
      <SelectBox
        options={[
          { key: 'M', value: '남자' },
          { key: 'F', value: '여자' },
          { key: 'N', value: '말하고 싶지 않아요' },
        ]}
        defaultValue={value}
        setDefaultValue={setValue}
      />
    ) : desc === '직업' ? (
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
      <DatePicker defaultValue={value} setDefaultValue={setValue} />
    );

  return (
    <div className="w-full flex flex-col items-center">
      <div className="w-full flex justify-between">
        <div className="flex items-center">
          <p className="w-[6rem] text-left custom-semibold-text text-custom-matcha">
            {desc}
          </p>
          <p className="custom-medium-text text-custom-black">
            {['성별', '직업'].includes(desc) ? keyValueMap.get(value) : value}
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
}
