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

public class MemberApiControllerTest extends MockMvcTest {
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
    
    @DisplayName("getAllMembers: 멤버 전체 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception {
        System.out.println("시작");
        // given : 테스트 실행 준비
        final String url = "/api/member";

        Member savedMember = memberRepository.save(new Member("홍길동"));
        System.out.println("given(테스트 실행 준비) 끝");
        
        // when : 테스트 진행
        System.out.println("when(테스트 진행) 시작");
        
        // mockMvc.perform() 메서드는 요청을 전송하는 역할 (postman의 request요청과 유사)
        // mockMvc.perform() 메서드의 반환 타입은 ResultActions 객체
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));
        // accept() 메서드는 요청을 보낼때 무슨 타입으로 응답을 받을건지를 결정하는 메서드. XML이 표준이긴 하지만 JSON 줄 것을 요청
        System.out.println("when(테스트 진행) 끝");
        
        // then : 결과 검증
        System.out.println("then(결과 검증) 시작");

        result.andExpect(MockMvcResultMatchers.status().isOk()) // 응답이 OK(200) 코드인지 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(savedMember.getName()));
        // JsonPath("$[0].${필드명}")은 JSON 응답값을 가져오는 메서드 : 0번째 배열에 들어있는 객체의 필드를 가져와라

        System.out.println("then(결과 검증) 끝");
        
    }
}