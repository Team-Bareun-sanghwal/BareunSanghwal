import { useState, useRef } from 'react';

interface IInputBoxProps {
  mode: 'NICKNAME' | 'HABITNICKNAME';
}

export const InputBox = ({ mode }: IInputBoxProps) => {
  const label = mode === 'NICKNAME' ? '닉네임' : '해빗 별칭';
  const placeholder =
    mode === 'NICKNAME'
      ? '2~10글자로 입력해주세요.'
      : '15자 이하로 입력해주세요.';
  const regExp =
    mode === 'NICKNAME'
      ? /[A-Za-z0-9ㄱ-ㅎ가-힣!?@#$%^&*]{2,10}/g
      : /[A-Za-z0-9ㄱ-ㅎ가-힣!?@#$%^&*]{1,15}/g;
  let lineColor = useRef<string>('border-b-custom-green');
  let guideText = useRef<string | null>(null);
  let guideTextColor = useRef<string>('text-custom-black');

  const [userInputData, setUserInputData] = useState<string | null>(null);

  return (
    <section className="w-[34rem] flex flex-col items-start gap-[0.5rem]">
      <label className="custom-semibold-text text-custom-matcha">{label}</label>
      <input
        className={`${lineColor.current} w-[34rem] bg-transparent custom-medium-text outline-none py-[0.5rem] border-b-[0.2rem]`}
        placeholder={placeholder}
        onChange={(event) => {
          // if (event.target.value) {
          //   if (regExp.test(event.target.value)) {
          //     setUserInputData(event.target.value);
          //     lineColor.current = 'border-b-custom-success';
          //     guideTextColor.current = 'text-custom-success';
          //     if (mode === 'NICKNAME') {
          //       guideText.current = '좋은 닉네임이네요!';
          //     } else if (mode === 'HABITNICKNAME') {
          //       guideText.current = '좋은 해빗 별칭입니다!';
          //     }
          //   } else {
          //     setUserInputData(null);
          //     lineColor.current = 'border-b-custom-error';
          //     guideTextColor.current = 'text-custom-error';
          //     if (mode === 'NICKNAME') {
          //       guideText.current =
          //         '2~10자의 한글, 영문, 숫자, 특수 기호 조합의 닉네임을 정해주세요.';
          //     } else if (mode === 'HABITNICKNAME') {
          //       guideText.current =
          //         '15자 이하의 한글, 영문, 숫자, 특수 기호 조합의 닉네임을 정해주세요.';
          //     }
          //   }
          // } else {
          //   lineColor.current = 'border-b-custom-error';
          //   guideTextColor.current = 'text-custom-error';
          //   if (mode === 'NICKNAME') {
          //     guideText.current = '닉네임이 없습니다. 입력해주세요.';
          //   } else if (mode === 'HABITNICKNAME') {
          //     guideText.current = '해빗 별칭이 없습니다. 입력해주세요.';
          //   }
          // }
        }}
      ></input>
      <span className={`${guideTextColor.current} custom-light-text`}>
        {guideText.current}
      </span>
    </section>
  );
};
