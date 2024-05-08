import { RecapContent } from '@/components';
import { getRecapDetail } from '../../_apis/getRecapDetail';

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
