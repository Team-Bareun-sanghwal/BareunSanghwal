import { $Fetch } from '@/apis';

export const getRecapDetail = async ({ recapId }: { recapId: string }) => {
  const result = await $Fetch({
    method: 'GET',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/recaps/${recapId}`,
    cache: 'default',
  });
  if ((await result.status) === 200) {
    return result.data;
  }
};
