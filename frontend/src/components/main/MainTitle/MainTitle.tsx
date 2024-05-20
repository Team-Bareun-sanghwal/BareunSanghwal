import { BellButton } from '@/components/notification/BellButton/BellButton';
import Image from 'next/image';
interface IHabitTrackerDto {
  name: string;
  alias: string;
  memberHabitId: number;
  habitTrackerId: number;
  icon: string;
  succeededTime: string;
  day: number;
}
interface IHabitTrackerTodayDtoList {
  habitTrackerTodayDtoList: IHabitTrackerDto[];
}
export const MainTitle = ({
  habitTrackerTodayDtoList,
}: IHabitTrackerTodayDtoList) => {
  const total = habitTrackerTodayDtoList?.length || 0;
  const succeed =
    habitTrackerTodayDtoList?.filter(
      (habit: IHabitTrackerDto) => habit.succeededTime != null,
    ).length || 0;

  return (
    <div className="flex mx-6 items-center h-16 justify-between">
      <div className="flex items-end gap-1">
        <div className="flex flex-col justify-center p-1">
          {total > 0 && total === succeed ? (
            <Image alt="" src="/images/icon-star.png" width={20} height={20} />
          ) : (
            <Image
              alt=""
              src="/images/icon-star-gray.png"
              width={20}
              height={20}
            />
          )}
        </div>
        <p className="text-3xl font-bold mx-2">오늘의 해빗</p>
      </div>
      <BellButton />
    </div>
  );
};
