export default function Page({ params }: { params: { recapId: string } }) {
  const { recapId } = params;

  return <>나는 리캡이로다 {recapId}</>;
}
