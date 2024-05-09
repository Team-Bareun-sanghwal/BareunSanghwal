import { $Fetch } from '@/apis';

export async function getPopularCategoryList() {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/rank`,
    cache: 'no-store',
  });

  if (response.status !== 200)
    throw new Error('최근 사람들이 등록하는 습관 10가지를 불러오는데 실패');
  return response;
}
