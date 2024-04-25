package life.bareun.diary.global.security.controller;

import jakarta.servlet.http.HttpServletResponse;
import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class TestController {

    private final MemberRepository memberRepository;

    @RequestMapping("/test")
    public String login(
        HttpServletResponse response
    ) {
        // response.sendRedirect("/oauth2/code/google");
        return "login";
    }

    @RequestMapping("/jpa-test")
    public String login() {
        String sub = "115475741758274788174";

        Member m1 = Member.create(sub, OAuth2Provider.GOOGLE);
        Member member = memberRepository.findBySub(sub).orElse(m1);

        System.out.println("m1: " + m1.getId());
        System.out.println("member: " + member.getId());
        System.out.println(m1 == member);

        return "login";
    }
}