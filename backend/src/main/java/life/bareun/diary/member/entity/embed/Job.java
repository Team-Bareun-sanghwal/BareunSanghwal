package life.bareun.diary.member.entity.embed;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Job {
    // STUDENT("STUDENT"),
    // EMPLOYEE("EMPLOYEE"),
    // HOUSEWIFE("HOUSEWIFE"),
    // JOB_SEEKER("JOB_SEEKER"),
    // SELF_EMPLOYED("SELF_EMPLOYED");

    STUDENT("학생"),
    EMPLOYEE("회사원"),
    HOUSEWIFE("주부"),
    JOB_SEEKER("취준생"),
    SELF_EMPLOYED("자영업");
    private final String value;
}
