import { RecapContent } from '@/components';

export default function Page({ params }: { params: { recapId: string } }) {
  // api 통신 추가
  // `${process.env.NEXT_PUBLIC_BASE_URL}/recaps/${recapId}`

  const { recapId } = params;

  const data = {
    year: 2024,
    month: 1,
    memberName: '감자도리',
    mostSucceededHabit: '자연 체험하기',
    mostSucceededMemberHabit: '점심 샐러드 먹기',
    averageRateByMemberHabit: 63.3,
    rateByMemberHabitList: [
      {
        name: '점심 샐러드 먹기',
        missCount: 1,
        actionCount: 4,
        ratio: 80,
      },
      {
        name: '12시 취침',
        missCount: 2,
        actionCount: 3,
        ratio: 60,
      },
      {
        name: '영양제 먹기',
        missCount: 2,
        actionCount: 3,
        ratio: 60,
      },
      {
        name: '웨이트 1시간',
        missCount: 3,
        actionCount: 2,
        ratio: 40,
      },
      {
        name: '강의장 계단으로 가기',
        missCount: 3,
        actionCount: 2,
        ratio: 40,
      },
    ],
    rateByHabitList: [
      {
        name: '자연 체험하기',
        ratio: 25,
      },
      {
        name: '디지털디톡스',
        ratio: 18,
      },
      {
        name: '오디오북 듣기',
        ratio: 18,
      },
      {
        name: '감사하기',
        ratio: 12,
      },
      {
        name: '기타',
        ratio: 27,
      },
    ],
    // mostSubmitTime: 'DAWN',
    // mostSubmitTime: 'MORNING',
    // mostSubmitTime: 'EVENING',
    mostSubmitTime: 'NIGHT',
    collectedStar: 13,
    myKeyWord: 'keyword',
    image: '/images/recap-image.png',
  };

  return (
    <>
      <RecapContent data={data} />
    </>
  );
}
