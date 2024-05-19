'use client';

import { useEffect, useState } from 'react';
import { HomeIcon } from '@heroicons/react/24/solid';
import {
  ChartPieIcon,
  Square3Stack3DIcon,
  GiftIcon,
  UserIcon,
} from '@heroicons/react/24/solid';
import Link from 'next/link';

interface INavBarProps {
  mode: 'HOME' | 'HABIT' | 'REWARD' | 'REPORT' | 'MY_INFO';
}

export default function NavBar({ mode }: INavBarProps) {
  const menus = {
    HOME: [
      <HomeIcon key={mode} className="w-[3rem] h-[3rem]" />,
      '홈',
      `/main`,
    ],
    HABIT: [
      <Square3Stack3DIcon key={mode} className="w-[3rem] h-[3rem]" />,
      '해빗',
      '/habit',
    ],
    REWARD: [
      <GiftIcon key={mode} className="w-[3rem] h-[3rem]" />,
      '보상',
      '/tree',
    ],
    REPORT: [
      <ChartPieIcon key={mode} className="w-[3rem] h-[3rem]" />,
      '리포트',
      '/statistics',
    ],
    MY_INFO: [
      <UserIcon key={mode} className="w-[3rem] h-[3rem]" />,
      '내 정보',
      '/mypage',
    ],
  };

  const [focusedMenu, setFocusedMenu] = useState('HOME');

  useEffect(() => {
    setFocusedMenu(mode);
  }, [mode]);

  return (
    <div className="w-dvw fixed bottom-0">
      <div className="h-[3rem] backdrop-blur-sm bg-white/10 -mb-[2rem] rounded-t-[1rem]"></div>

      <nav
        className={
          'h-[8rem] px-[1rem] py-[1.5rem] flex items-center justify-around rounded-t-[2rem] bg-custom-white border-t backdrop-blur-0'
        }
      >
        {Object.entries(menus).map((menu, index) => {
          const keyName = menu[0];
          const [icon, koreanKeyName, url] = menu[1];

          return (
            <Link href={url as string} key={`${keyName}-${index}`}>
              <section
                className={`flex flex-col gap-[0.5rem] items-center cursor-pointer ${keyName === focusedMenu ? 'text-custom-matcha' : 'text-custom-medium-gray'}`}
                onClick={() => setFocusedMenu(keyName)}
              >
                <>
                  {icon}
                  <p className="custom-light-text">{koreanKeyName}</p>
                </>
              </section>
            </Link>
          );
        })}
      </nav>
    </div>
  );
}
