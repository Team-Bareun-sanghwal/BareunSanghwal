'use client';

import {
  CircularChart3DComponent,
  CircularChart3DSeriesCollectionDirective,
  CircularChart3DSeriesDirective,
  PieSeries3D,
  CircularChartDataLabel3D,
  Inject,
} from '@syncfusion/ej2-react-charts';
import { registerLicense } from '@syncfusion/ej2-base';
import { GradientBar } from '../GradientBar/GradientBar';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

interface IHabitType {
  habit: string;
  rate: number;
}

interface IPropType {
  rateByHabitList: IHabitType[];
  mostSuccessedHabit: string;
}

const labelConfig = {
  visible: true,
  position: 'Outside',
  template:
    '<div style="text-align: center; max-width: 6rem; font-weight:200; font-size:1.2rem;"><p>${point.x}</p><p style="font-weight:400; font-size:1.6rem;">${point.y}%</p></div>',
  font: {
    fontFamily: 'pretendard, sans-serif',
    color: 'white',
  },
  connectorStyle: {
    width: 1,
    color: 'rgba(255, 255, 255, 0.5)',
    length: '20',
  },
};

export const RecapPieChart = ({
  rateByHabitList,
  mostSuccessedHabit,
}: IPropType) => {
  return (
    <div className="w-full flex flex-col items-center">
      <CircularChart3DComponent
        id="RecapPieChart"
        rotation={30}
        tilt={5}
        depth={40}
        width="300"
      >
        <Inject services={[PieSeries3D, CircularChartDataLabel3D]} />
        <CircularChart3DSeriesCollectionDirective>
          <CircularChart3DSeriesDirective
            dataSource={rateByHabitList}
            xName="habit"
            yName="rate"
            radius="80%"
            innerRadius="40%"
            dataLabel={labelConfig}
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
