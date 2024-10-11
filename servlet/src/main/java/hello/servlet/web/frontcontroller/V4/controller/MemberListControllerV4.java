package hello.servlet.web.frontcontroller.V4.controller;

import java.util.Map;

import ch.qos.logback.core.model.Model;

import java.util.List;

import hello.domain.member.Member;
import hello.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.V4.ControllerV4;

public class MemberListControllerV4 implements ControllerV4 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        return "members";
    }

}
