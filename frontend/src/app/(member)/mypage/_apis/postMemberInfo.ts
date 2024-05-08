import { $Fetch } from '@/apis';

interface IPropType {
  [key: string]: string | null;
}

export const postMemberInfo = async ({ data }: { data: IPropType }) => {
  const result = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members`,
    cache: 'default',
    data: data,
  });
  return result;
};
