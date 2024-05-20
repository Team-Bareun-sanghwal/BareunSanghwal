'use client';

import {
  AxisModel,
  Category,
  ChartComponent,
  Inject,
  SeriesCollectionDirective,
  SeriesDirective,
  SplineAreaSeries,
  Tooltip,
} from '@syncfusion/ej2-react-charts';
import { registerLicense } from '@syncfusion/ej2-base';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

interface IDataType {
  time: number;
  value: number;
}

export const StatisticsSpineAreaGraph = ({ data }: { data: IDataType[] }) => {
  let maxValue = -1;
  data.map((now) => (now.value > maxValue ? (maxValue = now.value) : maxValue));

  const primaryXAxisConfig: AxisModel = {
    valueType: 'Category',
    majorTickLines: { width: 0 },
    majorGridLines: { width: 0 },
    desiredIntervals: 5,
    labelStyle: {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '200',
      size: '12',
      color: '#d4d4d8',
    },
  };

  const primaryYAxisConfig: AxisModel = {
    visible: false,
    maximum: maxValue + 3,
  };

  const chartAreaConfig = {
    border: {
      width: 0,
    },
  };

  const tooltipConfig = {
    enable: true,
    enableMarker: false,
    shared: true,
    duration: 1500,
    fadeOutDuration: 1500,
    location: { x: 20, y: 0 },
    header: '',
    format: '${point.x}:00 | ${point.y}번',
    fill: '#edeef7',
    textStyle: {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '400',
      size: '12',
      color: '#27272a',
    },
  };

  return (
    <ChartComponent
      id="charts"
      primaryXAxis={primaryXAxisConfig}
      primaryYAxis={primaryYAxisConfig}
      chartArea={chartAreaConfig}
      height="170px"
      className="w-full"
      tooltip={tooltipConfig}
    >
      <Inject
        services={[SplineAreaSeries, SplineAreaSeries, Tooltip, Category]}
      />
      <SeriesCollectionDirective>
        <SeriesDirective
          dataSource={data}
          xName="time"
          yName="value"
          type="SplineArea"
          dashArray="5"
          opacity={0.7}
          fill="#94439e"
        />
      </SeriesCollectionDirective>
    </ChartComponent>
  );
};
