'use client';

import { useState } from 'react';
import { InputBox } from '@/components/common/InputBox/InputBox';
import { SelectBox } from '@/components/common/SelectBox/SelectBox';
import { ScrollDatePicker } from '@/components/common/ScrollDatePicker/ScrollDatePicker';
import { Button } from '@/components/common/Button/Button';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';
import { useOverlay } from '@/hooks/use-overlay';
import { $Fetch } from '@/apis';

export const SignInForm = () => {
  const overlay = useOverlay();

  const [nickname, setNickname] = useState('');
  const [sex, setSex] = useState('');
  const [job, setJob] = useState('');
  const [birthDay, setBirthday] = useState('2024-01-01');
  const [isShowPicker, setIsShowPicker] = useState(false);

  const toggleIsShowPicker = () => {
    setIsShowPicker((prev) => !prev);
  };

  const sexOptions = [
    { key: 'M', value: '남자' },
    { key: 'F', value: '여자' },
    { key: 'N', value: '말하고 싶지 않아요' },
  ];

  const jobOptions = [
    { key: 'STUDENT', value: '학생' },
    { key: 'EMPLOYEE', value: '회사원' },
    { key: 'HOUSEWIFE', value: '주부' },
    { key: 'JOB_SEEKER', value: '취준생' },
    { key: 'SELF_EMPLOYED', value: '자영업' },
  ];

  const submitForm = () => {
    if (nickname === '' || sex === '' || job === '') {
      console.log('ono');
      overlay.open(({ isOpen }) => (
        <AlertBox
          label="앗! 필요한 정보가 아직 남았어요"
          mode="WARNING"
          open={isOpen}
        />
      ));
      setTimeout(() => overlay.close(), 2000);
    } else {
      // api 통신 추가
    }
  };

  return (
    <div className="bg-custom-white w-[36rem] p-[1rem] flex flex-col justify-between h-full">
      <div className="flex flex-col gap-[2.5rem]">
        <InputBox
          isLabel={true}
          mode={'NICKNAME'}
          defaultValue={nickname}
          setDefaultValue={setNickname}
        />
        <SelectBox
          label="성별"
          options={sexOptions}
          defaultValue={sex}
          setDefaultValue={setSex}
        />
        <SelectBox
          label="직업"
          options={jobOptions}
          defaultValue={job}
          setDefaultValue={setJob}
        />
        <div onClick={toggleIsShowPicker}>
          <div className="flex justify-between items-center">
            <p className="custom-semibold-text text-custom-matcha">생일</p>
            <p className="custom-light-text">{birthDay}</p>
          </div>
          {isShowPicker ? (
            <ScrollDatePicker birthDay={birthDay} setBirthDay={setBirthday} />
          ) : null}
        </div>
      </div>

      <Button
        isActivated={true}
        label="바른생활 시작하기"
        onClick={submitForm}
      />
    </div>
  );
};
