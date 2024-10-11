package hello.servlet.web.frontcontroller.V4.controller;

import java.util.Map;

import hello.domain.member.Member;
import hello.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.V4.ControllerV4;

public class MemberSaveControllerV4 implements ControllerV4 { // this changes a lot

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        System.out.println("MemberSaveControllerV1.process()");
        String username = paramMap.get("username");

        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);

        memberRepository.save(member);

        model.put("member", member);
        return "save-result";
    }

}
