'use client';

import {
  AccumulationChartComponent,
  AccumulationDataLabel,
  AccumulationLegend,
  AccumulationSeriesCollectionDirective,
  AccumulationSeriesDirective,
  Inject,
  LegendPosition,
} from '@syncfusion/ej2-react-charts';

import { registerLicense } from '@syncfusion/ej2-base';

interface IHabitType {
  habit: string;
  value: number;
}

interface legendSettingsType {
  visible: boolean;
  shapeHeight: number;
  shapeWidth: number;
  position: LegendPosition;
  width: string;
  textStyle: {
    fontFamily: string;
    fontWeight: string;
    size: string;
  };
}

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

export const StatisticsPieChart = ({ data }: { data: IHabitType[] }) => {
  const labelSettings = {
    visible: true,
    position: 'Inside',
    name: 'percentage',
    font: {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '200',
      size: '10',
    },
  };

  const legendSettings: legendSettingsType = {
    visible: true,
    shapeHeight: 10,
    shapeWidth: 10,
    position: 'Right',
    width: '100',
    textStyle: {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '200',
      size: '10',
    },
  };

  const processedData = data.map((habit) => {
    return { ...habit, percentage: habit.value + '%' };
  });

  return (
    <AccumulationChartComponent
      id="statisticsPieChart"
      legendSettings={legendSettings}
      height="250px"
    >
      <Inject services={[AccumulationLegend, AccumulationDataLabel]} />
      <AccumulationSeriesCollectionDirective>
        <AccumulationSeriesDirective
          dataSource={processedData}
          xName="habit"
          yName="value"
          explode={true}
          explodeOffset="10%"
          explodeIndex={0}
          legendShape="Circle"
          dataLabel={labelSettings}
        ></AccumulationSeriesDirective>
      </AccumulationSeriesCollectionDirective>
    </AccumulationChartComponent>
  );
};
