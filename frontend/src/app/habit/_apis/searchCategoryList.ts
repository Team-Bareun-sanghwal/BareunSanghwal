import { $Fetch } from '@/apis';

export async function searchCategoryList(category: string) {
  const response = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/habits/search?habitName=${category}`,
    cache: 'no-store',
  });

  if (response.status !== 200) throw new Error('해빗 카테고리 검색 실패');
  return response;
}
