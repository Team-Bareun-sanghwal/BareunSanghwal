import { NavBar } from '@/components/common/NavBar/NavBar';
import { RecapBarChart } from '@/components/recap/RecapBarChart/RecapBarChart';

const data = [
  {
    habit: '12시 취침',
    missCount: 2,
    actionCount: 15,
    ratio: 80,
  },
  {
    habit: '영양제 먹기',
    missCount: 3,
    actionCount: 27,
    ratio: 90,
  },
  {
    habit: '점심 샐러드 먹기',
    missCount: 8,
    actionCount: 27,
    ratio: 78,
  },
  {
    habit: '강의장 계단으로 가기',
    missCount: 3,
    actionCount: 37,
    ratio: 93,
  },
  {
    habit: '웨이트 1시간',
    missCount: 5,
    actionCount: 37,
    ratio: 89,
  },
];

export default function Home() {
  return (
    <div>
      <div className="h-72 bg-custom-black"></div>
      <RecapBarChart rateByHabitList={data} />
      {/* <main className="custom-bold-text text-custom-light-green">
        hello, world!
      </main> */}

      {/* <NavBar mode="HOME" /> */}
    </div>
  );
}
