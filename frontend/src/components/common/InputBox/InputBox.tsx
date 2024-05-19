'use client';

import { useState } from 'react';

interface IInputBoxProps {
  isLabel: boolean;
  mode: 'NICKNAME' | 'HABITNICKNAME';
  defaultValue: string;
  setDefaultValue: (newValue: string) => void;
}

export const InputBox = ({
  isLabel,
  mode,
  defaultValue,
  setDefaultValue,
}: IInputBoxProps) => {
  const label = mode === 'NICKNAME' ? '닉네임' : '해빗 별칭을 지어주세요';
  const placeholder =
    mode === 'NICKNAME'
      ? '2~12자로 입력해주세요.'
      : '15자 이하로 입력해주세요.';
  const regExp =
    mode === 'NICKNAME'
      ? /^[A-Za-z0-9ㄱ-ㅎ가-힣!?@#$%^&*\s]{2,12}$/
      : /^[A-Za-z0-9ㄱ-ㅎ가-힣!?@#$%^&*\s]{1,15}$/;

  const [lineColor, setLineColor] = useState<string>(
    defaultValue ? 'border-b-custom-yellow-green' : 'border-b-custom-error',
  );

  const [guideText, setGuideText] = useState<string>(() =>
    mode === 'NICKNAME'
      ? defaultValue
        ? ''
        : '닉네임이 없습니다. 입력해주세요.'
      : '해빗 별칭이 없습니다. 입력해주세요.',
  );

  const [guideTextColor, setGuideTextColor] = useState<string>(
    defaultValue ? 'text-custom-yellow-green' : 'text-custom-error',
  );

  return (
    <section className="w-full flex flex-col items-start gap-[0.5rem]">
      {isLabel && (
        <label className="custom-semibold-text text-custom-matcha">
          {label}
        </label>
      )}

      <input
        className={`${lineColor} w-full bg-transparent custom-medium-text outline-none py-[0.5rem] border-b-[0.2rem] rounded-none`}
        placeholder={placeholder}
        defaultValue={defaultValue}
        onChange={(event) => {
          if (
            event.target.value.length !== 0 &&
            event.target.value.replaceAll(' ', '').length !== 0
          ) {
            if (regExp.test(event.target.value)) {
              setDefaultValue(event.target.value);
              setLineColor('border-b-custom-yellow-green');
              setGuideTextColor('text-custom-yellow-green');
              if (mode === 'NICKNAME') {
                setGuideText('좋은 닉네임이네요!');
              } else if (mode === 'HABITNICKNAME') {
                setGuideText('좋은 해빗 별칭입니다!');
              }
            } else {
              setDefaultValue('');
              setLineColor('border-b-custom-error');
              setGuideTextColor('text-custom-error');
              if (mode === 'NICKNAME') {
                setGuideText(
                  '2~12자의 한글, 영문, 숫자, 특수 기호 조합의 닉네임을 정해주세요.',
                );
              } else if (mode === 'HABITNICKNAME') {
                setGuideText(
                  '15자 이하의 한글, 영문, 숫자, 특수 기호 조합의 닉네임을 정해주세요.',
                );
              }
            }
          } else {
            setLineColor('border-b-custom-error');
            setGuideTextColor('text-custom-error');
            if (mode === 'NICKNAME') {
              setGuideText('닉네임이 없습니다. 입력해주세요.');
            } else if (mode === 'HABITNICKNAME') {
              setGuideText('해빗 별칭이 없습니다. 입력해주세요.');
            }
          }
        }}
      ></input>
      <span className={`${guideTextColor} custom-light-text`}>{guideText}</span>
    </section>
  );
};
