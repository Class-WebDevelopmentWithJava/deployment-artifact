package com.itschool.springbootdeveloper.controller;

import com.itschool.springbootdeveloper.controller.base.CrudController;
import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.network.request.MemberRequest;
import com.itschool.springbootdeveloper.network.response.MemberResponse;
import com.itschool.springbootdeveloper.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Presentation 계층 : 웹 클라이언트의 요청 및 응답을 처리
// RESTController(REST API, json) 또는 Controller 어노테이션은 라우터 역할을 함
// 라우터는 HTTP 요청과 메서드를 연결하는 장치
// @RestController, @Controller 애너테이션 또한 @Component 포함하고 있음
@RestController // TestController 클래스를 빈으로 등록, 스프링에서 사용할 객체, 싱글톤
@RequestMapping("/api/member")
public class MemberApiController extends CrudController<MemberRequest, MemberResponse, Member> {
    @Autowired
    public MemberApiController(MemberService baseService) { // MemberService
        super(baseService);
    }

    @Override
    protected MemberService getBaseService() {
        return (MemberService) baseService;
    }
}
