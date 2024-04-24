import React, {
  createContext,
  PropsWithChildren,
  ReactNode,
  useCallback,
  useMemo,
  useState,
} from 'react';

export const OverlayContext = createContext<{
  // Provider를 만들기 위한 Context 생성, 추후에 Root Layout에 적용 예정
  mount(id: string, element: ReactNode): void;
  unmount(id: string): void;
} | null>(null);

export const OverlayProvider = ({ children }: PropsWithChildren) => {
  // Map 객체로 각 overlay(bottomsheet, modal, confirm 등) 관리
  const [overlayById, setOverlayById] = useState<Map<string, ReactNode>>(
    new Map(),
  );

  const mount = useCallback((id: string, element: ReactNode) => {
    // 서로 다른 Map 객체로 관리되기 때문에 Overlay 여러 개를 동시에 제어 가능
    setOverlayById((overlayById) => {
      const cloned = new Map(overlayById);
      cloned.set(id, element);
      return cloned;
    });
  }, []);

  const unmount = useCallback((id: string) => {
    setOverlayById((overlayById) => {
      const cloned = new Map(overlayById);
      cloned.delete(id);
      return cloned;
    });
  }, []);

  const context = useMemo(() => ({ mount, unmount }), [mount, unmount]);

  return (
    <OverlayContext.Provider value={context}>
      {children}
      {[...overlayById.entries()].map(([id, element]) => (
        <React.Fragment key={id}>{element}</React.Fragment>
      ))}
    </OverlayContext.Provider>
  );
};
