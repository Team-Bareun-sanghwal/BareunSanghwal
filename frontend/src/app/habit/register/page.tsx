import { getPopularCategoryList } from '../_apis/getPopularCategoryList';
import { HabitRegisterFunnel } from './_components/HabitRegisterFunnel';

export default async function Page() {
  const popularCategoryList = await getPopularCategoryList();
  const popularCategoryListData = popularCategoryList.data.habitList;

  // 나와 비슷한 사람들이 하는 해빗 10개를 응답하는 API가 완성되지 않음
  // const similarCategoryList = await getSimilarCategoryList();
  // const similarCategoryListData = similarCategoryList.data.habitList;
  const similarCategoryListData = popularCategoryListData;

  return (
    <HabitRegisterFunnel
      popularCategoryListData={popularCategoryListData}
      similarCategoryListData={similarCategoryListData}
    />
  );
}
