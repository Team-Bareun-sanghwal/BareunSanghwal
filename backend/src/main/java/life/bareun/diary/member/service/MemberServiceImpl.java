package life.bareun.diary.member.service;

import java.util.concurrent.atomic.AtomicBoolean;
import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.principal.MemberPrincipal;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.dto.request.MemberUpdateDtoReq;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;
    private final StreakService streakService;

    @Transactional(readOnly = true)
    public boolean existsBySub(String sub) {
        return memberRepository.existsBySub(sub);
    }


    @Override
    @Transactional
    public MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider) {
        AtomicBoolean isNewMember = new AtomicBoolean(false);
        Member member = memberRepository.findBySub(sub).orElseGet(() -> {
            isNewMember.set(true);
            Member saveMember = memberRepository.save(Member.create(sub, oAuth2Provider));

            streakService.createInitialMemberStreak(saveMember);

            return saveMember;
        });

        return new MemberPrincipal(member.getId(), member.getRole(), member.getProvider(), isNewMember.get());
    }

    @Override
    @Transactional
    public void update(MemberUpdateDtoReq memberUpdateDtoReq) {
        Long id = AuthUtil.getMemberIdFromAuthentication();

        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
            );

        member.update(memberUpdateDtoReq);
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void delete() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        memberRepository.deleteById(id);
    }

}
