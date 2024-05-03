import { getPopularCategoryList } from '../_apis/getPopularCategoryList';
import { HabitRegisterFunnel } from './_components/HabitRegisterFunnel';

export default async function Page() {
  const popularCategoryList = await getPopularCategoryList();
  const popularCategoryListData = popularCategoryList.data.habitList;

  return (
    <HabitRegisterFunnel popularCategoryListData={popularCategoryListData} />
  );
}
