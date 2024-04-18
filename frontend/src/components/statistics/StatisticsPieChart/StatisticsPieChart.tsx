'use client';

import {
  AccumulationChartComponent,
  AccumulationLegend,
  AccumulationSeriesCollectionDirective,
  AccumulationSeriesDirective,
  Inject,
} from '@syncfusion/ej2-react-charts';

import { registerLicense } from '@syncfusion/ej2-base';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

export const StatisticsPieChart = () => {
  const data: any[] = [
    { x: '위스키 마시기', y: 45, text: '위스키 마시기', fill: '#00226C' },
    { x: '와인 마시기', y: 53, text: '와인 마시기', fill: '#0450C2' },
    { x: '축구', y: 56, text: '축구', fill: '#0073DC' },
    { x: '악기 연주', y: 61, text: '악기 연주', fill: '#0D98FF' },
    { x: '독서하기', y: 40, text: '독서하기', fill: '#9CD9FF' },
    { x: '기타', y: 20, text: '기타', fill: '#0450C2' },
  ];
  const legendSettings = {
    visible: true,
    shapeHeight: 10,
    shapeWidth: 10,
    position: 'Right',
    width: '120',
    textStyle: {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '200',
      size: '10',
    },
  };

  return (
    <AccumulationChartComponent
      id="statisticsPieChart"
      legendSettings={legendSettings}
    >
      <Inject services={[AccumulationLegend]} />
      <AccumulationSeriesCollectionDirective>
        <AccumulationSeriesDirective
          dataSource={data}
          xName="x"
          yName="y"
          legendShape="Circle"
        ></AccumulationSeriesDirective>
      </AccumulationSeriesCollectionDirective>
    </AccumulationChartComponent>
  );
};
