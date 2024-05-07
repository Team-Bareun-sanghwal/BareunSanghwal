import { NavBar, RecapContentBox, TabBox } from '@/components';
import { StatisticsContent } from '@/components/statistics/StatisticsContent/StatisticsContent';

const recapTotalData = [
  {
    year: 2024,
    recapList: [
      {
        recapId: 16,
        image: 'basic',
        period: new Date('2024-04-26'),
      },
      {
        recapId: 18,
        image: 'basic',
        period: new Date('2024-03-26'),
      },
      {
        recapId: 19,
        image: 'basic',
        period: new Date('2024-02-26'),
      },
      {
        recapId: 19,
        image: 'basic',
        period: new Date('2024-01-26'),
      },
    ],
  },
  {
    year: 2023,
    recapList: [
      {
        recapId: 5,
        image: 'basic',
        period: new Date('2023-12-26'),
      },
      {
        recapId: 7,
        image: 'basic',
        period: new Date('2023-11-26'),
      },
    ],
  },
];

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

export default function Page() {
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
