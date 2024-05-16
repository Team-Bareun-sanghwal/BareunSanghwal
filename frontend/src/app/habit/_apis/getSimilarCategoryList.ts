import { $Fetch } from '@/apis';

export async function getSimilarCategoryList() {
  const response = await $Fetch({
    method: 'GET',
    url: `https://bareun.life/recommendation/habits`,
    cache: 'no-store',
  });

  // if (response.status !== 200)
  //   throw new Error('추천 습관 10가지를 불러오는데 실패');

  return response;
}
