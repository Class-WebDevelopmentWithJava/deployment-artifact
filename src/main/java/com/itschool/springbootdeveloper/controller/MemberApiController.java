package com.itschool.springbootdeveloper.controller;

import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Presentation 계층 : 웹 클라이언트의 요청 및 응답을 처리
// RESTController(REST API, json) 또는 Controller 어노테이션은 라우터 역할을 함
// 라우터는 HTTP 요청과 메서드를 연결하는 장치
// @RestController, @Controller 애너테이션 또한 @Component 포함하고 있음
@RestController // TestController 클래스를 빈으로 등록, 스프링에서 사용할 객체, 싱글톤
@RequestMapping("/api/member")
public class MemberApiController { // 프레젠테이션 계층
    
    // Presentation 계층 <-> Service 계층
    @Autowired // testService 빈 주입
    MemberService memberService;
    
    @GetMapping("") // /test 요청이 오면 하단의 test() 메서드 실행
    public List<Member> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return members;
    }

    @GetMapping("{id}")
    public Member findMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @GetMapping("search-by-name/{name}")
    public List<Member> findMembersByName(@PathVariable String name) {
        return memberService.findMembersByName(name);
    }

    // GetMapping으로 메소드 추가
    // id와 name 둘 다 받아서 데이터를 return
    // repository : findByIdAndName()
    @GetMapping("{id}/{name}")
    public String getMemberByIdAndName(@PathVariable Long id,
                                       @PathVariable String name) {
        return "테스트 : " + id + name;
    }

    @GetMapping("test")
    public String test() {
        memberService.test();

        return "test-api";
    }
}
