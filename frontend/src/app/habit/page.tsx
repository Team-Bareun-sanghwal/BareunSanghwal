import { TabBox, GuideBox, HabitListBox, NavBar } from '@/components';
import { HabitUpdateComponent } from './_components/HabitUpdateComponent';
import { getActivatedHabitList } from './_apis/getActivatedHabitList';
import { getCompletedHabitList } from './_apis/getAllCompletedHabitList';

export default async function Page() {
  const activatedHabitList = await getActivatedHabitList();
  // console.log(activatedHabitList);

  const completedHabitList = await getCompletedHabitList();
  // console.log(completedHabitList);

  return (
    <>
      <div className="p-[1rem] flex flex-col gap-[1rem]">
        <span className="custom-bold-text">나의 해빗</span>

        <TabBox
          tabs={[
            {
              component: <HabitUpdateComponent />,
              title: '진행 중인 해빗',
            },
            {
              component: (
                <>
                  <GuideBox guideText="이제 더 이상 기록하지 않는 해빗 목록이에요. 이전에 기록한 내용은 계속 열람할 수 있습니다." />

                  <HabitListBox
                    alias="물 2L 마시기"
                    completedAt={new Date('2024-04-23T00:00:00.000Z')}
                    createdAt={new Date('2024-03-22T00:00:00.000Z')}
                    iconSrc="/images/icon-clock.png"
                    mode="COMPLETED"
                    name="건강하기"
                  />
                </>
              ),
              title: '완료한 해빗',
            },
          ]}
        />
      </div>

      <NavBar mode="HABIT" />
    </>
  );
}
