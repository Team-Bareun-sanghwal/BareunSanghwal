'use client';

import {
  CircularChart3DComponent,
  CircularChart3DSeriesCollectionDirective,
  CircularChart3DSeriesDirective,
  PieSeries3D,
  CircularChartDataLabel3D,
  Inject,
  CircularChartLegend3D,
} from '@syncfusion/ej2-react-charts';
import { registerLicense } from '@syncfusion/ej2-base';
import { GradientBar } from '../GradientBar/GradientBar';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

interface IHabitType {
  name: string;
  ratio: number;
}

interface IPropType {
  rateByHabitList: IHabitType[];
  mostSuccessedHabit: string;
}

const legendSettings = {
  visible: true,
  width: '300',
  textStyle: {
    fontFamily: 'pretendard, sans-serif',
    color: 'white',
    size: '12px',
    opacity: 0.8,
  },
  toggleVisibility: false,
  textOverflow: 'Clip',
};

const labelConfig = {
  visible: true,
  position: 'Inside',
  format: '{value}%',
  font: {
    fontFamily: 'pretendard, sans-serif',
    color: 'white',
    size: '20px',
    opacity: 0.8,
  },
};

export const RecapPieChart = ({
  rateByHabitList,
  mostSuccessedHabit,
}: IPropType) => {
  return (
    <div className="w-full flex h-[50rem] flex-col items-center overflow-hidden">
      <CircularChart3DComponent
        id="RecapPieChart"
        rotation={30}
        tilt={5}
        depth={40}
        width="400"
        legendSettings={legendSettings}
      >
        <Inject
          services={[
            PieSeries3D,
            CircularChartDataLabel3D,
            CircularChartLegend3D,
          ]}
        />
        <CircularChart3DSeriesCollectionDirective>
          <CircularChart3DSeriesDirective
            dataSource={rateByHabitList}
            xName="name"
            yName="ratio"
            radius="80%"
            innerRadius="40%"
            dataLabel={labelConfig}
            legendShape="Rectangle"
          ></CircularChart3DSeriesDirective>
        </CircularChart3DSeriesCollectionDirective>
      </CircularChart3DComponent>
      <div className="w-full">
        <GradientBar
          textFront="가장 많이 달성한 해빗은 "
          textMiddle={mostSuccessedHabit}
          textBack=" !"
        />
      </div>
    </div>
  );
};
