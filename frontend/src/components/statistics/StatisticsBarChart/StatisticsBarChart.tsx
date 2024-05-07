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
  ValueType,
} from '@syncfusion/ej2-react-charts';

import { registerLicense } from '@syncfusion/ej2-base';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

interface IDayType {
  day: string;
  value: number;
  colorIdx: number;
}

interface XAxisConfigType {
  visible: boolean;
  valueType: ValueType;
  majorGridLines?: { width: number };
  majorTickLines?: { width: number };
  lineStyle?: { width: number };
  labelStyle: {
    fontFamily: string;
    fontWeight: string;
    size: string;
    color: string;
  };
}

export const StatisticsBarChart = ({ data }: { data: IDayType[] }) => {
  const borderRadius = 10;
  let maxValue = 0;

  const colorArr = ['#277530', '#d4d4d8', '#94439e'];

  const processedData = data.map((day) => {
    maxValue = Math.max(maxValue, day.value);
    return { ...day, color: colorArr[day.colorIdx] };
  });

  const xAxisConfig: XAxisConfigType = {
    visible: true,
    valueType: 'Category',
    majorGridLines: { width: 0 },
    majorTickLines: { width: 0 },
    lineStyle: { width: 0 },
    labelStyle: {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '400',
      size: '12',
      color: '#52525b',
    },
  };

  const yAxisConfig = {
    visible: false,
    minimum: 0,
    maximum: maxValue + 20,
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
        size: '14',
        color: '#52525b',
      },
    },
  };

  const legendSettings = { visible: true };

  return (
    <ChartComponent
      id="statisticsBarChart"
      primaryXAxis={xAxisConfig}
      primaryYAxis={yAxisConfig}
      chartArea={chatAreaConfig}
      legendSettings={legendSettings}
      height="250px"
      width="250px"
    >
      <Inject services={[ColumnSeries, Legend, Tooltip, DataLabel, Category]} />
      <SeriesCollectionDirective>
        <SeriesDirective
          dataSource={processedData}
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
          pointColorMapping="color"
        />
      </SeriesCollectionDirective>
    </ChartComponent>
  );
};
