import {
  TabBox,
  GuideBox,
  HabitListBox,
  NavBar,
  ErrorPage,
} from '@/components';
import { HabitUpdateComponent } from './_components/HabitUpdateComponent';
import { getActivatedHabitList } from './_apis/getActivatedHabitList';
import { getCompletedHabitList } from './_apis/getAllCompletedHabitList';
import { ICompletedHabit } from './_types';

export default async function Page() {
  const activatedHabitList = await getActivatedHabitList();
  const activatedHabitListData = activatedHabitList.data.memberHabitList;
  console.log(activatedHabitListData);

  const completedHabitList = await getCompletedHabitList();
  const completedHabitListData = completedHabitList.data.memberHabitList;

  return (
    <>
      <div className="p-[1rem] flex flex-col gap-[1rem] pb-[11rem]">
        <span className="custom-bold-text">나의 해빗</span>

        <TabBox
          tabs={[
            {
              component:
                activatedHabitListData.length === 0 ? (
                  <div className="mt-[10rem]">
                    <ErrorPage
                      errorTitle="등록한 해빗이 없어요"
                      errorDescription="해빗을 등록하고 관리하세요"
                      buttonText="등록하러 가기"
                      nextPage="/habit/register"
                    />
                  </div>
                ) : (
                  <HabitUpdateComponent
                    activatedHabitListData={activatedHabitListData}
                  />
                ),
              title: '진행 중인 해빗',
            },
            {
              component:
                completedHabitListData.length === 0 ? (
                  <span className="mx-auto custom-semibold-text text-custom-medium-gray">
                    완료한 해빗이 없습니다
                  </span>
                ) : (
                  <>
                    <GuideBox guideText="이제 더 이상 기록하지 않는 해빗 목록이에요. 이전에 기록한 내용은 계속 열람할 수 있습니다." />

                    {completedHabitListData?.map(
                      (completedHabit: ICompletedHabit) => {
                        return (
                          <HabitListBox
                            key={`completedHabit-${completedHabit.memberHabitId}`}
                            alias={completedHabit.alias}
                            name={completedHabit.name}
                            iconSrc={completedHabit.icon}
                            memberHabitId={completedHabit.memberHabitId}
                            mode="COMPLETED"
                            completedAt={new Date(completedHabit.succeededTime)}
                            createdAt={new Date(completedHabit.createdAt)}
                          />
                        );
                      },
                    )}
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
