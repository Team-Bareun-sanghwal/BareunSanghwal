import { ErrorPage, NavBar, RecapContentBox, TabBox } from '@/components';
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
      component:
        recapTotalData.length === 0 ? (
          <div className="h-full flex items-center">
            <ErrorPage
              errorTitle="리캡을 만들 충분한 데이터가 없어요"
              errorDescription="모든 해빗 트래커를 2번 이상만 달성해보세요!"
              buttonText=""
              nextPage=""
            />
          </div>
        ) : (
          <RecapContentBox recapTotalData={recapTotalData} />
        ),
    },
  ];

  return (
    <>
      <div className="px-[1rem] pt-[1rem] pb-[11rem] flex flex-col gap-[1rem] min-h-full">
        <span className="text-custom-black custom-bold-text">리포트</span>
        <TabBox tabs={tabs} />
      </div>
      <NavBar mode="REPORT" />
    </>
  );
}
