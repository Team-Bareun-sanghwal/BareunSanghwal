package life.bareun.diary.member.repository;

import java.util.List;
import life.bareun.diary.global.notification.dto.NotificationDateDto;
import life.bareun.diary.member.entity.Member;

public interface MemberRepositoryCustom {
    List<Member> findAllUnaccompaniedMember(NotificationDateDto notificationDateDto);
}
