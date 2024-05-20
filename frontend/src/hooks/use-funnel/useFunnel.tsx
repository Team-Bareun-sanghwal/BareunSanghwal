import { ReactNode, isValidElement, FC, useState } from 'react';

type StepProps = {
  name: string;
  children: ReactNode;
};

type FunnelProps = {
  children: ReactNode[];
};

interface FunnelComponent extends FC<FunnelProps> {
  Step: FC<StepProps>;
}

type UseFunnelReturn<T> = {
  Funnel: FunnelComponent;
  setStep: (step: T) => void;
};

export function useFunnel<T>(initialStep: T): UseFunnelReturn<T> {
  const [step, setStep] = useState<T>(initialStep);

  const Step: FC<StepProps> = ({ children }) => {
    return <>{children}</>;
  };

  const Funnel: FunnelComponent = ({ children }) => {
    const currentStep = children.find(
      (child: ReactNode) => isValidElement(child) && child.props.name === step,
    );
    return <>{currentStep}</> || <></>;
  };

  Funnel.Step = Step;
  return { Funnel, setStep };
}
