package life.bareun.diary.member.entity.embed;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Job {
     STUDENT("STUDENT"),
     EMPLOYEE("EMPLOYEE"),
     HOUSEWIFE("HOUSEWIFE"),
     JOB_SEEKER("JOB_SEEKER"),
     SELF_EMPLOYED("SELF_EMPLOYED");

    private final String value;
}
