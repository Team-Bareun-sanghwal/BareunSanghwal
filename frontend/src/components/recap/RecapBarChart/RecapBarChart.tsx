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
  LegendPosition,
} from '@syncfusion/ej2-react-charts';
import { registerLicense } from '@syncfusion/ej2-base';

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

// interface legendSettingsType {
//   visible: boolean;
//   shapeHeight: number;
//   shapeWidth: number;
//   position: LegendPosition;
//   width: string;
//   textStyle: {
//     fontFamily: string;
//     fontWeight: string;
//     size: string;
//   };
// }

// const legendSettings = {
//   visible: false,
// };

const xAxisConfig: Chart3DAxisModel = {
  valueType: 'Category',
  visible: true,
  majorGridLines: { width: 0 },
  majorTickLines: { width: 0 },
};

const yAxisConfig: Chart3DAxisModel = {
  visible: false,
  majorGridLines: { width: 0 },
  majorTickLines: { width: 0 },
};

const pointRender = (args) => {
  let seriesColor = [
    '#00bdae',
    '#404041',
    '#357cd2',
    '#e56590',
    '#f8b883',
    '#70ad47',
    '#dd8abd',
    '#7f84e8',
    '#7bb4eb',
    '#ea7a57',
  ];
  args.fill = seriesColor[2 * args.point.index + args.point.series.index];

  // console.log(args.point.series.properties.dataLabel.properties.format);
  // args.point.series.properties.dataLabel.properties.format = '{value}잉잉';
};

const textRender = (args) => {
  console.log(args.point.index);
  console.log(args.point.series.properties.dataSource[args.point.index]);
  const indexedData = args.point.series.properties.dataSource[args.point.index];
  args.text = `${indexedData.habit} <br> ${indexedData.ratio}%`;
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
  margin: {
    bottom: 20,
  },
};

export const RecapBarChart = ({
  rateByHabitList,
}: {
  rateByHabitList: IDataType[];
}) => {
  return (
    <Chart3DComponent
      id="RecapBarChart"
      primaryXAxis={xAxisConfig}
      primaryYAxis={yAxisConfig}
      pointRender={pointRender}
      textRender={textRender}
      wallColor="transparent"
      enableRotation={false}
      rotation={20}
      perspectiveAngle={300}
      depth={100}
      className="w-full bg-custom-black"
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
          dataSource={rateByHabitList}
          xName="habit"
          yName="actionCount"
          type="StackingColumn"
          columnSpacing={-0.4}
          fill="red"
        ></Chart3DSeriesDirective>
        <Chart3DSeriesDirective
          dataSource={rateByHabitList}
          xName="habit"
          yName="missCount"
          type="StackingColumn"
          columnSpacing={-0.4}
          fill="green"
          dataLabel={labelSettings}
        ></Chart3DSeriesDirective>
      </Chart3DSeriesCollectionDirective>
    </Chart3DComponent>
  );
};
