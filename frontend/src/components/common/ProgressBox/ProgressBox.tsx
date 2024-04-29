'use client';

import { CheckCircleIcon } from '@heroicons/react/24/solid';

interface ProgressBoxProps {
  stages: string[];
  beforeStageIndex: number;
}

const StageBox = ({
  color,
  stageTitle,
}: {
  color: string;
  stageTitle: string;
}) => {
  return (
    <nav className={`${color} flex flex-col gap-[0.2rem] items-center`}>
      <CheckCircleIcon className="w-[2.4rem] h-[2.4rem]" />
      <span className="custom-light-text">{stageTitle}</span>
    </nav>
  );
};

export const ProgressBox = ({ stages, beforeStageIndex }: ProgressBoxProps) => {
  const processedStages = stages.map((stage, index) => {
    return (
      <StageBox
        key={`stage-${index}`}
        color={
          index <= beforeStageIndex
            ? 'text-custom-green'
            : 'text-custom-medium-gray'
        }
        stageTitle={stage}
      />
    );
  });

  for (let i = stages.length - 1; i >= 1; i--) {
    processedStages.splice(
      i,
      0,
      <div
        className={`w-[3rem] h-[0.2rem] rounded-lg ${
          i <= beforeStageIndex + 1
            ? 'bg-custom-green'
            : 'bg-custom-medium-gray'
        }`}
      ></div>,
    );
  }

  return (
    <section className="w-full flex justify-evenly items-center px-[1rem]">
      {processedStages.map((element, index) => {
        return <div key={`element-${index}`}>{element}</div>;
      })}
    </section>
  );
};
