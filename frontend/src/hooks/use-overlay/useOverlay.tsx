import { useContext, useMemo, useRef, useState } from 'react';
import { OverlayContext } from './OverlayProvider';
import { OverlayController, IOverlayControlRef } from './OverlayController';
import { CreateOverlayElement } from './types';

let elementId = 1;

export const useOverlay = () => {
  const context = useContext(OverlayContext);

  if (context === null) {
    throw new Error('OverlayProvider 내부에서만 useOverlay 사용 가능합니다.');
  }

  const { mount, unmount } = context;
  const [id] = useState(() => String(elementId++)); // overlay마다 id를 1씩 증가시켜 관리

  const overlayRef = useRef<IOverlayControlRef | null>(null);

  return useMemo(
    () => ({
      open: (overlayElement: CreateOverlayElement) => {
        mount(
          id,
          <OverlayController
            ref={overlayRef}
            overlayElement={overlayElement}
            onExit={() => {
              unmount(id);
            }}
          />,
        );
      },
      close: () => {
        overlayRef.current?.close();
      },
      exit: () => {
        unmount(id);
      },
    }),
    [id, mount, unmount],
  );
};
