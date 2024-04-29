import { useState, PropsWithChildren } from 'react';

type ChildrenType = {
  name: string;
};

export const useFunnel = (initialStep: string) => {
  const [step, setStep] = useState<string>(initialStep);

  const Step = (props: PropsWithChildren<ChildrenType>) => {
    return <>{props.children}</>;
  };

  const Funnel = ({
    children,
  }: {
    children: PropsWithChildren<ChildrenType>[];
  }) => {
    const targetStep = children?.find((props) => props.name === step);
    return { ...targetStep };
  };

  Funnel.Step = Step;

  return [Funnel, setStep];
};
