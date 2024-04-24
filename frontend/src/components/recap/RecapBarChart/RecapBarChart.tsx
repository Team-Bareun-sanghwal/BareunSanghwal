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
  ColumnSeries3D,
  Highlight3D,
} from '@syncfusion/ej2-react-charts';
import { registerLicense } from '@syncfusion/ej2-base';

registerLicense(
  process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    ? process.env.NEXT_PUBLIC_SYNCFUSION_KEY
    : '',
);

const data = [
  { country: 'USA', gold: 50, silver: 70, bronze: 45 },
  { country: 'China', gold: 40, silver: 60, bronze: 55 },
  { country: 'Japan', gold: 70, silver: 60, bronze: 50 },
  { country: 'Australia', gold: 60, silver: 56, bronze: 40 },
  { country: 'France', gold: 50, silver: 45, bronze: 35 },
  { country: 'Germany', gold: 40, silver: 30, bronze: 22 },
  { country: 'Italy', gold: 40, silver: 35, bronze: 37 },
  { country: 'Sweden', gold: 30, silver: 25, bronze: 27 },
];

export const RecapBarChart = () => {
  return (
    <Chart3DComponent
      id="charts"
      primaryXAxis={{
        valueType: 'Category',
      }}
      wallColor="transparent"
      enableRotation={true}
      rotation={7}
      tilt={10}
      depth={100}
    >
      <Inject
        services={[
          ColumnSeries3D,
          Category3D,
          Legend3D,
          Tooltip3D,
          DataLabel3D,
          Highlight3D,
        ]}
      />
      <Chart3DSeriesCollectionDirective>
        <Chart3DSeriesDirective
          dataSource={data}
          xName="country"
          yName="gold"
          type="Column"
          fill="red"
        ></Chart3DSeriesDirective>
      </Chart3DSeriesCollectionDirective>
    </Chart3DComponent>
  );
};
