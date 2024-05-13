import { getPopularCategoryList } from '../_apis/getPopularCategoryList';
import { getSimilarCategoryList } from '../_apis/getSimilarCategoryList';
import { getUserAmountByDay } from '../_apis/getUserAmountByDay';
import { getSimpleActivatedHabitList } from '../_apis/getSimpleActivatedHabitList';
import { HabitRegisterFunnel } from './_components/HabitRegisterFunnel';

export default async function Page() {
  const popularCategoryList = await getPopularCategoryList();
  const popularCategoryListData = popularCategoryList.data.habitList;

  // 나와 비슷한 사람들이 하는 해빗 10개를 응답하는 API가 완성되지 않음
  const similarCategoryList = await getSimilarCategoryList();
  const similarCategoryListData = similarCategoryList.data.habitList;
  console.log(similarCategoryListData);

  const userAmountByDay = await getUserAmountByDay();
  const userAmountData = userAmountByDay.data;

  const simpleHabitList = await getSimpleActivatedHabitList();
  const simpleHabitListData = simpleHabitList.data.memberHabitList;

  return (
    <HabitRegisterFunnel
      popularCategoryListData={popularCategoryListData}
      similarCategoryListData={similarCategoryListData}
      userAmountData={userAmountData}
      simpleHabitListData={simpleHabitListData}
    />
  );
}
