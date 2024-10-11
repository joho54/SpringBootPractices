package hello.servlet.web.frontcontroller.v3.controller;

import java.util.Map;

import hello.domain.member.Member;
import hello.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

public class MemberSaveControllerV3 implements ControllerV3 { // this changes a lot

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        System.out.println("MemberSaveControllerV3.process()");
        
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);

        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");
        
        System.out.println("mv inf: " + mv.getViewName());
        mv.getModel().put("member", member);
        return mv;
    }

}
