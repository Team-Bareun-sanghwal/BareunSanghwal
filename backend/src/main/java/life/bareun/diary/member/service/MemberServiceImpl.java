package life.bareun.diary.member.service;

import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.dto.MemberPrincipal;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public boolean existsBySub(String sub) {
        return memberRepository.existsBySub(sub);
    }

    // @Override
    // @Transactional
    // public String login(String sub) {
    // 	Member member = memberRepository.findBySub(sub)
    // 		.orElseThrow(
    // 			() -> new MemberException(MemberErrorCode.NO_SUCH_USER)
    // 		);
    //
    // 	return tokenProvider.createToken(member.getId(), member.getRole());
    // }

    @Override
    @Transactional
    public MemberPrincipal register(String sub, OAuth2Provider oAuth2Provider) {
		/* todo
		1. member 존재 유무 검사
		  1-1. 있으면 그냥 그 member를 가져온다.
		  1-2. 없으면 DB에 삽입
		2. 인증 정보 등록을 위한 MemberPrincipal 반환
		*/
        Member member = memberRepository.findBySub(sub)
            .orElseGet(() -> memberRepository.save(Member.create(sub, oAuth2Provider)));

        return new MemberPrincipal(
            member.getId(),
            member.getRole(),
            member.getProvider()
        );
    }

    @Override
    public void update() {
        Member member = memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
