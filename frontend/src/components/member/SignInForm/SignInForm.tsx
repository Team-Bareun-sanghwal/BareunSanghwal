'use client';

import { useState } from 'react';
import { InputBox } from '@/components/common/InputBox/InputBox';
import { SelectBox } from '@/components/common/SelectBox/SelectBox';
import { DatePicker } from '@/components/common/DatePicker/DatePicker';
import { Button } from '@/components/common/Button/Button';
import { AlertBox } from '@/components/common/AlertBox/AlertBox';
import { useOverlay } from '@/hooks/use-overlay';
import { useRouter } from 'next/navigation';
import { convertBirthday } from '@/components/common/Picker/utils';
import { postMemberInfo } from '@/app/(member)/mypage/_apis/postMemberInfo';

export const SignInForm = () => {
  const overlay = useOverlay();
  const router = useRouter();

  const [nickname, setNickname] = useState('');
  const [gender, setGender] = useState('');
  const [job, setJob] = useState('');
  const [birthDay, setBirthday] = useState('2024-01-01');

  const genderOptions = [
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

  const submitForm = async () => {
    if (nickname === '' || gender === '' || job === '') {
      overlay.open(({ isOpen }) => (
        <AlertBox
          label="앗! 필요한 정보가 아직 남았어요"
          mode="WARNING"
          open={isOpen}
        />
      ));
      setTimeout(() => overlay.close(), 2000);
    } else {
      const data = {
        nickname: nickname,
        birthDate: convertBirthday(birthDay),
        gender: gender,
        job: job,
      };
      const result = await postMemberInfo({ data });
      if ((await result.status) === 200) {
        router.push('/main');
      }
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
          options={genderOptions}
          defaultValue={gender}
          setDefaultValue={setGender}
        />
        <SelectBox
          label="직업"
          options={jobOptions}
          defaultValue={job}
          setDefaultValue={setJob}
        />
        <div>
          <div className="flex justify-between items-center mb-[1rem]">
            <p className="custom-semibold-text text-custom-matcha">생일</p>
          </div>
          <DatePicker defaultValue={birthDay} setDefaultValue={setBirthday} />
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
