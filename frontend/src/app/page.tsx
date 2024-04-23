import Calender from '@/components/Calender/Calender';
import { StreaksResponse, setDayInfo } from '@/app/mock';
import { ColorThemeResponse } from './mock';
import { NavBar } from '@/components/common/NavBar/NavBar';
export default function Home() {
  const { dayOfWeekFirst, memberHabitList, dayInfo } = StreaksResponse;
  const theme = ColorThemeResponse.streak_theme;
  return (
    <>
      <>
        <main className="custom-bold-text text-custom-light-green">
          hello, world!
        </main>
        <Calender
          dayOfWeekFirst={dayOfWeekFirst}
          memberHabitList={memberHabitList}
          dayInfo={dayInfo}
          themeColor={theme}
        />
      </>
      <NavBar mode="HOME" />
    </>
  );
}
