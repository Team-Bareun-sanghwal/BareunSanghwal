import { HabitWriteComponent } from './_components/HabitWriteComponent';
import { Complete } from './_components/Complete';

export default async function Page() {
  // 진행 중인 해빗 목록 fetch
  // 완료한 해빗 목록 fetch
  return <HabitWriteComponent />;
  // return <Complete />;
}
