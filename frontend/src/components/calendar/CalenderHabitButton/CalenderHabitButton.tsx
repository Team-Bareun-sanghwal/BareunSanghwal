import Image from 'next/image';
export const CalenderHabitButton = ({
  memberHabitId,
  alias,
  icon,
  habitId,
  setHabitId,
}: {
  memberHabitId: number;
  alias: string;
  icon: string;
  habitId: number;
  setHabitId: React.Dispatch<React.SetStateAction<number>>;
}) => {
  const onClickHabit = () => {
    if (habitId == memberHabitId) setHabitId(-1);
    else setHabitId(memberHabitId);
  };

  return (
    <>
      <div className="flex flex-col items-center ">
        <button
          onClick={onClickHabit}
          className={
            memberHabitId == habitId
              ? 'relative bg-gray-300 text-2xl w-16 h-16 rounded-full '
              : 'relative bg-custom-sky-pastel text-2xl w-16 h-16 rounded-full'
          }
        >
          {icon === 'All' ? (
            <div className="flex items-center justify-center">
              <Image
                src="/images/icon-block1.png"
                width={40}
                height={40}
                alt=""
              />
            </div>
          ) : (
            icon
          )}
        </button>

        <p className={'text-xs text-center w-16 truncate ...'}>{alias}</p>
      </div>
    </>
  );
};
