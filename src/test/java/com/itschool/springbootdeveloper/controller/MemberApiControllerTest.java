package com.itschool.springbootdeveloper.controller;

import com.itschool.springbootdeveloper.MockMvcTest;
import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class MemberApiControllerTest extends MockMvcTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void BeforeCleanUp() {
        memberRepository.deleteAll();
        System.out.println(this.getClass().getName() + " 테이블 전부 delete");
    }

    @AfterEach // 테스트 실행 후 실행하는 메서드
    public void AfterCleanUp() {
        memberRepository.deleteAll();
        System.out.println(this.getClass().getName() + " 테이블 전부 delete");
    }

    @DisplayName("getAllMembers: 멤버 전체에 성공한다.")
    @Test
    public void getAllMemebers() throws Exception {
        System.out.println("시작");
        // given : 테스트 실행 준비
        final String url = "/api/member";
        Member savedMember = memberRepository.save(new Member("홍길동"));
        System.out.println("given 끝");

        // when : 테스트 진행
        // perform() 메서드는 요청을 전송하는 역할 (postman의 request 요청)
        // perform() 메서드의 반환 타입은 ResultActions 객체
        System.out.println("when 시작");
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON));
        // accept() 메서드는 요청을 보낼 때 무슨 타입으로 응답을 받을지 결정하는 메서드. XML이 표준이긴 하지만 JSON으로 줄 것을 요청
        System.out.println("when 끝");

        // then : 테스트 결과 검증
        // JsonPath("$[0].${필드명}")은 JSON 응답값을 가져오는 메서드 : 0번째 배열에 들어있는 객체의 필드를 가져옴
        System.out.println("then 시작");
        result.andExpect(MockMvcResultMatchers.status().isOk()) // 응답이 OK(200) 코드인지 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(savedMember.getName()));
        System.out.println("then 끝");
    }
}