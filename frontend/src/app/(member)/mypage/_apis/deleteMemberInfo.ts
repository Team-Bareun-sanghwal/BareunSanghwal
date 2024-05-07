import { $Fetch } from '@/apis';

export const deleteMemberInfo = async () => {
  const result = await $Fetch({
    method: 'DELETE',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members`,
    cache: 'default',
  });
  if ((await result.status) === 200) {
    return result.status;
  }
};
