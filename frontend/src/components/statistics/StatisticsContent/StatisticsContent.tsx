import { $Fetch } from '@/apis';
import { StatisticsPieChart } from '../StatisticsPieChart/StatisticsPieChart';
import { StatisticsBarChart } from '../StatisticsBarChart/StatisticsBarChart';
import { StatisticsSpineAreaGraph } from '../StatisticsSpineAreaGraph/StatisticsSpineAreaGraph';
import { ReportOnlyText } from '../ReportOnlyText/ReportOnlyText';
import { GuideBox } from '@/components/common/GuideBox/GuideBox';
import { ColoredSentence } from '@/components/common/ColoredSentence/ColoredSentence';
import { getStatisticsData } from '@/app/(statistics)/_apis/getStatisticsData';

let maxDayValue = 0;
let minDayValue = 999999;
let maxDay = '';
let minDay = '';

export const StatisticsContent = async () => {
  const data = await getStatisticsData();

  data.practiceCountsPerDayOfWeek.map(
    (day: { dayOfWeek: string; value: number }) => {
      if (maxDayValue < day.value) {
        maxDayValue = day.value;
        maxDay = day.dayOfWeek;
      }
      if (day.value < minDayValue) {
        minDayValue = day.value;
        minDay = day.dayOfWeek;
      }
    },
  );

  return (
    <div className="py-[2rem] flex flex-col gap-[3rem]">
      <ReportOnlyText
        whole_days={data.totalDays}
        streak_days={data.streakDays}
        star_days={data.starredDays}
        longest_streak={data.longestStreak}
      />

      {data.maxPracticedHabit ? (
        <>
          <div>
            <p className="custom-medium-text">상위 달성 해빗</p>
            <StatisticsPieChart data={data.practicedHabitsTop} />
            <GuideBox
              guideText={
                <ColoredSentence
                  textFront={'내가 가장 잘 달성한 해빗은 '}
                  textMiddle={data.maxPracticedHabit}
                  textBack={' !'}
                />
              }
            />
          </div>

          <div className="flex flex-col items-center">
            <p className="custom-medium-text mr-auto">요일 통계</p>
            <StatisticsBarChart data={data.practiceCountsPerDayOfWeek} />
            <div className="h-[2rem]" />
            <GuideBox
              guideText={
                <div>
                  <ColoredSentence
                    textFront={'나는 주로 '}
                    textMiddle={`${maxDay}요일`}
                    textBack={'에 해빗을 많이 달성하고'}
                  />
                  <ColoredSentence
                    textFront={''}
                    textMiddle={`${minDay}요일`}
                    textBack={'에 적게 달성하는 편이에요'}
                  />
                </div>
              }
            />
          </div>

          <div>
            <p className="custom-medium-text">해빗 달성 시간</p>
            <StatisticsSpineAreaGraph data={data.practiceCountsPerHour} />
            <div className="h-[2rem]" />
            <GuideBox guideText="내가 해빗을 달성한 시간 분포를 보여주는 그래프예요" />
          </div>
        </>
      ) : (
        <GuideBox guideText="아직 리포트를 만들 충분한 데이터가 없어요! 해빗을 달성하고 리포트를 확인해보세요" />
      )}
    </div>
  );
};
