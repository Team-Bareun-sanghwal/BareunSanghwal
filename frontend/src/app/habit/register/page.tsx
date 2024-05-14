import { getPopularCategoryList } from '../_apis/getPopularCategoryList';
import { getSimilarCategoryList } from '../_apis/getSimilarCategoryList';
import { getUserAmountByDay } from '../_apis/getUserAmountByDay';
import { getSimpleActivatedHabitList } from '../_apis/getSimpleActivatedHabitList';
import { HabitRegisterFunnel } from './_components/HabitRegisterFunnel';

export default async function Page() {
  const popularCategoryList = await getPopularCategoryList();
  const popularCategoryListData = popularCategoryList.data.habitList;

  const similarCategoryList = await getSimilarCategoryList();
  const similarCategoryListData = similarCategoryList.data.habitList;

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
