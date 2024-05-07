import { NavBar, RecapContentBox, TabBox } from '@/components';
import { StatisticsContent } from '@/components/statistics/StatisticsContent/StatisticsContent';
import { getRecapList } from '../_apis/getRecapList';

export default async function Page() {
  const result = await getRecapList();
  const recapTotalData = result.recapGroupList;

  const tabs = [
    {
      title: '리포트',
      component: <StatisticsContent />,
    },
    {
      title: '리캡',
      component: <RecapContentBox recapTotalData={recapTotalData} />,
    },
  ];

  return (
    <>
      <div className="bg-custom-white px-[1rem] pt-[1rem] pb-[11rem] flex flex-col justify-between min-h-full">
        <p className="text-custom-black custom-bold-text mb-[4rem]">리포트</p>
        <TabBox tabs={tabs} />
      </div>
      <NavBar mode="REPORT" />
    </>
  );
}
