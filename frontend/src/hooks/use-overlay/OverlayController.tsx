import {
  forwardRef,
  Ref,
  useCallback,
  useEffect,
  useImperativeHandle,
  useState,
} from 'react';

import { CreateOverlayElement } from './types';

interface IProps {
  overlayElement: CreateOverlayElement;
  onExit: () => void;
}

export interface IOverlayControlRef {
  close: () => void;
}

export const OverlayController = forwardRef(function OverlayController(
  { overlayElement: OverlayElement, onExit }: IProps,
  ref: Ref<IOverlayControlRef>,
) {
  const [isOpenOverlay, setIsOpenOverlay] = useState<boolean>(false);

  const handleOverlayClose = useCallback(() => setIsOpenOverlay(false), []);

  // 아래 훅은 forwardRef 내에서만 사용 가능
  // 부모 컴포넌트에 노출된 ref 객체를 제어
  useImperativeHandle(
    ref,
    () => {
      return { close: handleOverlayClose };
    },
    [handleOverlayClose],
  );

  useEffect(() => {
    // open 시에 애니메이션이 블로킹되는 현상을 막기 위해
    // 애니메이션을 최적화 해주는 requestAnimationFrame을 사용
    requestAnimationFrame(() => {
      setIsOpenOverlay(true);
    });
  }, []);

  return (
    <OverlayElement
      isOpen={isOpenOverlay}
      close={handleOverlayClose}
      exit={onExit}
    />
  );
});
