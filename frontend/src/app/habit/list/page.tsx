import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { HabitContentBox } from '@/components';
import { getHabitWriteList } from '../_apis/getHabitWriteList';
import { IWriteListData } from '../_types';

export default async function Page() {
  const habitWriteList = await getHabitWriteList(1);
  const habitWriteListData = habitWriteList.data.habitTrackerGroupList;

  return (
    <div className="p-[1rem] flex flex-col gap-[1rem]">
      <nav className="flex self-start gap-[0.5rem] items-center mb-[1rem]">
        <ChevronLeftIcon className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray" />
        <span className="custom-bold-text">물 2L 마시기</span>
      </nav>

      <HabitContentBox habitTotalData={habitWriteListData} />
    </div>
  );
}
