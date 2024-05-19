import dynamic from 'next/dynamic';
import Link from 'next/link';
import { ChevronLeftIcon } from '@heroicons/react/24/solid';
// import { HabitContentBox } from '@/components';
import { getHabitWriteList } from '../../_apis/getHabitWriteList';

const HabitContentBox = dynamic(
  () => import('@/components/common/HabitContentBox/HabitContentBox'),
);

export default async function Page({
  params,
}: {
  params: { memberHabitId: number };
}) {
  const habitWriteList = await getHabitWriteList(params.memberHabitId);
  const habitWriteListData = habitWriteList.data.habitTrackerGroupList;

  return (
    <div className="p-[1rem] flex flex-col gap-[1rem]">
      <Link href="/habit">
        <nav className="flex self-start gap-[0.5rem] items-center mb-[1rem]">
          <ChevronLeftIcon className="w-[2.4rem] h-[2.4rem] text-custom-medium-gray" />
          <span className="custom-bold-text">해빗 트래커 목록</span>
        </nav>
      </Link>

      {habitWriteListData.length === 0 ? (
        <span className="mx-auto custom-semibold-text text-custom-medium-gray">
          기록된 내용이 없습니다
        </span>
      ) : (
        <HabitContentBox habitTotalData={habitWriteListData} />
      )}
    </div>
  );
}
