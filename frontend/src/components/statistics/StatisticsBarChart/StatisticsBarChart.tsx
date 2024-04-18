'use client';

import {
  ChartComponent,
  SeriesCollectionDirective,
  SeriesDirective,
  Inject,
  Legend,
  Category,
  Tooltip,
  DataLabel,
  ColumnSeries,
} from '@syncfusion/ej2-react-charts';

import { registerLicense } from '@syncfusion/ej2-base';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

export const StatisticsBarChart = () => {
  const borderRadius = 15;

  const colorBase = '#d4d4d8';
  const colorMax = '#94439e';
  const colorMin = '#277530';

  const data: any[] = [
    { day: '월', value: 45, color: colorBase },
    { day: '화', value: 53, color: colorBase },
    { day: '수', value: 56, color: colorBase },
    { day: '목', value: 61, color: colorBase },
    { day: '금', value: 40, color: colorBase },
    { day: '토', value: 20, color: colorBase },
    { day: '일', value: 20, color: colorBase },
  ];

  let maxValue = -Infinity;
  let minValue = Infinity;

  // 배열을 반복하면서 최대값과 최소값 업데이트
  data.forEach((item) => {
    if (item.value > maxValue) {
      maxValue = item.value;
    }
    if (item.value < minValue) {
      minValue = item.value;
    }
  });
  console.log(minValue);
  console.log(maxValue);

  const xAxisConfig = {
    visible: false,
    valueType: 'Category',
    majorGridLines: { width: 0 },
  };

  const yAxisConfig = {
    visible: false,
    minimum: 0,
    majorGridLines: { width: 0 },
    majorTickLines: { width: 0 },
    lineStyle: { width: 0 },
  };

  const chatAreaConfig = {
    border: { width: 0 },
  };

  const labelSettings = {
    dataLabel: {
      visible: true,
      font: {
        fontFamily: 'pretendard, sans-serif',
        fontWeight: '400',
        size: '10',
        color: colorBase,
      },
    },
  };

  return (
    <ChartComponent
      id="statisticsBarChart"
      primaryXAxis={xAxisConfig}
      primaryYAxis={yAxisConfig}
      chartArea={chatAreaConfig}
    >
      <Inject services={[ColumnSeries, Legend, Tooltip, DataLabel, Category]} />
      <SeriesCollectionDirective>
        <SeriesDirective
          fill={colorBase}
          dataSource={data}
          xName="day"
          yName="value"
          type="Column"
          marker={labelSettings}
          cornerRadius={{
            topLeft: borderRadius,
            topRight: borderRadius,
            bottomLeft: borderRadius,
            bottomRight: borderRadius,
          }}
          // colorName={`${colorBase} ${colorMax} ${colorMin}`}
        />
      </SeriesCollectionDirective>
    </ChartComponent>
  );
};
