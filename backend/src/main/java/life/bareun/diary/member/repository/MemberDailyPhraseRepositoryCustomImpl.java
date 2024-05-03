package life.bareun.diary.member.repository;

import static life.bareun.diary.member.entity.QMemberDailyPhrase.memberDailyPhrase;

import com.querydsl.jpa.impl.JPAQueryFactory;
import life.bareun.diary.global.notification.dto.NotificationPhraseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberDailyPhraseRepositoryCustomImpl implements MemberDailyPhraseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void modifyMemberDailyPhrase(NotificationPhraseDto notificationPhraseDto) {
        queryFactory.update(memberDailyPhrase)
            .set(memberDailyPhrase.dailyPhrase, notificationPhraseDto.dailyPhrase())
            .where(memberDailyPhrase.id.eq(notificationPhraseDto.memberDailyPhraseId())).execute();
    }
}
