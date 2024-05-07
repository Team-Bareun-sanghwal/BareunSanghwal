import { $Fetch } from '@/apis';

interface IPropType {
  title: string;
  data: string;
}

export const postMemberInfo = async ({ title, data }: IPropType) => {
  const requestData: { [key: string]: any } = {};
  requestData[`${title}`] = data;

  const result = await $Fetch({
    method: 'PATCH',
    url: `${process.env.NEXT_PUBLIC_BASE_URL}/members`,
    cache: 'default',
    data: requestData,
  });
};
