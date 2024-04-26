import Calender from '@/components/calender/Calender';
import { StreaksResponse, MemberStreakResponse, setDayInfo } from '@/app/mock';
import { ColorThemeResponse } from './mock';
import { NavBar } from '@/components/common/NavBar/NavBar';
import HabitChecker from '@/components/main/HabitChecker/HabitChecker';
import LongestStreak from '@/components/main/LongestStreak/LongestStreak';
import HabitBtnList from '@/components/calender/HabitBtnList/HabitBtnList';
import HabitBtn from '@/components/calender/HabitBtn/HabitBtn';
import { BellIcon } from '@heroicons/react/24/outline';
import Image from 'next/image';
import Point from '@/components/point/Point/Point';
import Item from '@/components/point/Item/Item';
import { ItemListResponse } from '@/app/mock';
import Pallete from '@/components/point/Pallete/Pallete';
import ColoredText from '@/components/point/ColoredText/ColoredText';
import { Picker } from '@/components/common/EmojiPicker/Picker';
export default function Home() {
  const { dayOfWeekFirst, memberHabitList, dayInfo } = StreaksResponse;
  const theme = ColorThemeResponse.streak_theme;
  const getStar =
    dayInfo[22].achieveCount === dayInfo[22].totalCount &&
    dayInfo[22].totalCount !== 0;
  return (
    <>
      <div className="w-full h-40 bg-blue-300" />
      <div className="w-full h-40 bg-blue-300" />
      <div className="w-full h-40 bg-blue-300" />
      <div className="w-full h-40 bg-blue-300" />
      <Picker label={'해빗 아이콘을 지정해주세요'} />
    </>
  );
}
