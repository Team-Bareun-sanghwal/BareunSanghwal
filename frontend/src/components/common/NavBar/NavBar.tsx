'use client';

import { useEffect, useState } from 'react';
import { HomeIcon } from '@heroicons/react/20/solid';
import {
  ChartPieIcon,
  Square3Stack3DIcon,
  GiftIcon,
  UserIcon,
} from '@heroicons/react/24/solid';

interface INavBarProps {
  mode: 'HOME' | 'HABIT' | 'REWARD' | 'REPORT' | 'MY_INFO';
  onClick?: () => void;
}

export const NavBar = ({ mode, ...props }: INavBarProps) => {
  const menus = {
    HOME: [<HomeIcon className="w-[3rem] h-[3rem]" />, '홈'],
    HABIT: [<Square3Stack3DIcon className="w-[3rem] h-[3rem]" />, '해빗'],
    REWARD: [<GiftIcon className="w-[3rem] h-[3rem]" />, '보상'],
    REPORT: [<ChartPieIcon className="w-[3rem] h-[3rem]" />, '리포트'],
    MY_INFO: [<UserIcon className="w-[3rem] h-[3rem]" />, '내 정보'],
  };

  const [focusedMenu, setFocusedMenu] = useState('HOME');

  useEffect(() => {
    setFocusedMenu(mode);
  }, [mode]);

  return (
    <div className="w-[36rem] fixed bottom-0">
      <div className="h-[3rem] backdrop-blur-sm bg-white/10 -mb-[2rem] rounded-t-[1rem]"></div>

      <nav
        className={
          'h-[8rem] px-[1rem] py-[1.5rem] flex items-center justify-around rounded-t-[2rem] bg-custom-white border-t backdrop-blur-0'
        }
        {...props}
      >
        {Object.entries(menus).map((menu, index) => {
          const keyName = menu[0];
          const [icon, koreanKeyName] = menu[1];

          return (
            <section
              className={`flex flex-col gap-[0.5rem] items-center cursor-pointer ${keyName === focusedMenu ? 'text-custom-yellow-green' : 'text-custom-medium-gray'}`}
              onClick={() => setFocusedMenu(keyName)}
              key={`${keyName}-${index}`}
            >
              <>
                {icon}
                <p className="custom-light-text">{koreanKeyName}</p>
              </>
            </section>
          );
        })}
      </nav>
    </div>
  );
};
