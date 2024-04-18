import Calender from '@/components/Calender/Calender';
import { StreaksResponse, setDayInfo } from '@/components/Calender/mockserver';
import { NavBar } from '@/components/common/NavBar/NavBar';

export default function Home() {
  const { dayOfWeekFirst, memberHabitList, dayInfo } = StreaksResponse;
  const theme = 'bareun-sanghwal';
  const theme1 = 'rose';
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
          themeColor={theme1}
        />
      </>
      <NavBar mode="HOME" />
    </>
  );
}
