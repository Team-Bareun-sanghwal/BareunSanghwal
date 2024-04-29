'use client';

import {
  Chart3DComponent,
  Chart3DSeriesCollectionDirective,
  Chart3DSeriesDirective,
  Category3D,
  Inject,
  Legend3D,
  DataLabel3D,
  Tooltip3D,
  Highlight3D,
  Chart3DAxisModel,
  StackingColumnSeries3D,
} from '@syncfusion/ej2-react-charts';
import { registerLicense } from '@syncfusion/ej2-base';
import { GradientBar } from '../GradientBar/GradientBar';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

interface IDataType {
  habit: string;
  missCount: number;
  actionCount: number;
  ratio: number;
}

const xAxisConfig: Chart3DAxisModel = {
  visible: true,
  valueType: 'Category',
  labelStyle: {
    fontFamily: 'pretendard, sans-serif',
    fontWeight: '200',
    size: '12',
    color: 'white',
  },
  labelIntersectAction: 'Wrap',
  labelPadding: 25,
  majorGridLines: { width: 0 },
  majorTickLines: { width: 0 },
};

const yAxisConfig: Chart3DAxisModel = {
  visible: false,
  majorGridLines: { width: 0 },
  majorTickLines: { width: 0 },
};

const pointRender = (args: {
  fill: string;
  point: { index: number; series: { index: number } };
}) => {
  let seriesColor = [
    '#357CD2',
    '#FFFFFF',
    '#E56590',
    '#FFFFFF',
    '#8BD173',
    '#FFFFFF',
    '#00BDAE',
    '#FFFFFF',
    '#F8B883',
    '#FFFFFF',
    '#DD8ABD',
    '#FFFFFF',
  ];

  args.fill = seriesColor[2 * args.point.index + args.point.series.index];
};

const textRender = (args: {
  point: {
    series: {
      properties: {
        dataSource: { ratio: number; missCount: number; actionCount: number }[];
      };
    };
    index: number;
  };
  text: string;
  series: { index: number };
  textStyle: {
    fontFamily: string;
    fontWeight: string;
    size: string;
    color: string;
  };
}) => {
  const indexedData = args.point.series.properties.dataSource[args.point.index];
  if (args.series.index === 2) {
    let maxRatio = -Infinity;

    for (const item of args.point.series.properties.dataSource) {
      if (item.ratio > maxRatio) {
        maxRatio = item.ratio;
      }
    }

    args.text = `${indexedData.ratio}%`;
    args.textStyle = {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '400',
      size: '16',
      color: indexedData.ratio === maxRatio ? '#5bb227' : 'white',
    };
  } else {
    args.text = `${indexedData.missCount + indexedData.actionCount}일 중`;
    args.textStyle = {
      fontFamily: 'pretendard, sans-serif',
      fontWeight: '200',
      size: '10',
      color: 'white',
    };
  }
};

const labelSettings = {
  visible: true,
  position: 'Bottom',
  font: {
    fontFamily: 'pretendard, sans-serif',
    fontWeight: '200',
    size: '12',
    color: 'white',
  },
};

export const RecapBarChart = ({
  rateByMemberHabitList,
  averageRateByMemberHabit,
}: {
  rateByMemberHabitList: IDataType[];
  averageRateByMemberHabit: number;
}) => {
  const processedData = rateByMemberHabitList.map((data) => {
    return { ...data, size: 5 };
  });

  return (
    <div className="w-full flex flex-col items-center">
      <Chart3DComponent
        id="RecapBarChart"
        primaryXAxis={xAxisConfig}
        primaryYAxis={yAxisConfig}
        pointRender={pointRender}
        textRender={textRender}
        perspectiveAngle={120}
        depth={200}
        tilt={-20}
        width="360"
        height="500"
        wallColor="transparent"
      >
        <Inject
          services={[
            StackingColumnSeries3D,
            Category3D,
            Legend3D,
            Tooltip3D,
            DataLabel3D,
            Highlight3D,
          ]}
        />
        <Chart3DSeriesCollectionDirective>
          <Chart3DSeriesDirective
            dataSource={processedData}
            xName="habit"
            yName="actionCount"
            type="StackingColumn"
            columnSpacing={-0.2}
          ></Chart3DSeriesDirective>
          <Chart3DSeriesDirective
            dataSource={processedData}
            xName="habit"
            yName="missCount"
            type="StackingColumn"
            opacity={0.1}
            columnSpacing={-0.2}
          ></Chart3DSeriesDirective>
          <Chart3DSeriesDirective
            dataSource={processedData}
            xName="habit"
            yName="size"
            type="StackingColumn"
            opacity={0}
            columnSpacing={-0.2}
            dataLabel={labelSettings}
          ></Chart3DSeriesDirective>
          <Chart3DSeriesDirective
            dataSource={processedData}
            xName="habit"
            yName="size"
            type="StackingColumn"
            opacity={0}
            columnSpacing={-0.2}
            dataLabel={labelSettings}
          ></Chart3DSeriesDirective>
        </Chart3DSeriesCollectionDirective>
      </Chart3DComponent>
      <div className="w-full">
        <GradientBar
          textFront="대단한데요! 평균적으로 "
          textMiddle={`${averageRateByMemberHabit}%`}
          textBack="를 달성했어요!"
        />
      </div>
    </div>
  );
};
