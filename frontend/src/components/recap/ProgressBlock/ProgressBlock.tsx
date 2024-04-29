interface IPropType {
  nowIdx: number;
  pageIdx: number;
  increasePageIdx: () => void;
}

export const ProgressBlock = ({
  nowIdx,
  pageIdx,
  increasePageIdx,
}: IPropType) => {
  const bgColor = nowIdx <= pageIdx ? 'bg-white' : 'bg-custom-dark-gray';

  const interval = 1000; // 1초마다 갱신

  // progress 상태를 1초마다 갱신
  if (nowIdx < pageIdx) {
    setInterval(() => {
      increasePageIdx();
    }, interval);
  }

  // 언마운트 시 타이머 해제

  return <div className={`${bgColor} w-[3.3rem] h-[0.5rem] rounded-full`} />;
};
