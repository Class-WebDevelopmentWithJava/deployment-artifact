package com.itschool.springbootdeveloper.controller;

import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.network.request.MemberRequest;
import com.itschool.springbootdeveloper.network.response.MemberResponse;
import com.itschool.springbootdeveloper.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Presentation 계층 : 웹 클라이언트의 요청 및 응답을 처리
// RESTController(REST API, json) 또는 Controller 어노테이션은 라우터 역할을 함
// 라우터는 HTTP 요청과 메서드를 연결하는 장치
// @RestController, @Controller 애너테이션 또한 @Component 포함하고 있음
@RestController // TestController 클래스를 빈으로 등록, 스프링에서 사용할 객체, 싱글톤
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController { // 프레젠테이션 계층
    
    // Presentation 계층 <-> Service 계층
    private final MemberService memberService;

    @GetMapping("{id}")
    public ResponseEntity<MemberResponse> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.read(id));
    }

    @GetMapping("")
    public ResponseEntity<List<MemberResponse>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(memberService.readAll());
    }

    @PostMapping("")
    public ResponseEntity<MemberResponse> create(@RequestBody MemberRequest request) {
        // 200 OK, 201 Created, 400 Bad Request...
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.create(request));
    }

    // update http method? put(전체 수정), patch(부분 수정), patch는 이 과정에서는 생략
    @PutMapping("{id}")
    public ResponseEntity<MemberResponse> update(@PathVariable Long id,
                                                  @RequestBody MemberRequest request) {
        MemberResponse response = memberService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    // delete http method? delete(단건 삭제)
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        memberService.delete(id); // return 값 확인
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}
