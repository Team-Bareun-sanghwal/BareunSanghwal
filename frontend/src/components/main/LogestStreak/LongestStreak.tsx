interface ILogestStreakProps {
  longestStreakCount: number;
}
const LongestStreak = ({ longestStreakCount }: ILogestStreakProps) => {
  return (
    <>
      <div className="flex flex-col w-4/12 bg-custom-sky-pastel justify-center rounded-xl max-w-48 min-h-20">
        {longestStreakCount !== 0 ? (
          <>
            <p className="text-center text-lg ">오늘로</p>
            <p className="text-center text-2xl font-semibold">
              {longestStreakCount}일째
            </p>
          </>
        ) : (
          <>
            <p className="text-center text-base font-semibold">
              연속된 스트릭이 없어요..
            </p>
            <p className="text-center text-sm ">해빗을 꾸준히 달성해봐요! </p>
          </>
        )}
      </div>
    </>
  );
};
export default LongestStreak;
