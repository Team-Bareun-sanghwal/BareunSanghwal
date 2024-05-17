import dynamic from 'next/dynamic';

import { getRecapDetail } from '../../_apis/getRecapDetail';

const RecapContent = dynamic(
  () => import('@/components/recap/RecapContent/RecapContent'),
);

export default async function Page({
  params,
}: {
  params: { recapId: string };
}) {
  const { recapId } = params;
  const data = await getRecapDetail({ recapId: recapId });

  return (
    <>
      <RecapContent data={data} />
    </>
  );
}
