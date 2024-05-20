package life.bareun.diary.global.notification.dto.response;

import java.util.List;
import life.bareun.diary.global.notification.dto.NotificationDto;
import lombok.Builder;

@Builder
public record NotificationListResDto(

    List<NotificationDto> notificationList

) { }
