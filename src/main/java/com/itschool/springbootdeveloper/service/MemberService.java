package com.itschool.springbootdeveloper.service;

import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.network.request.MemberRequest;
import com.itschool.springbootdeveloper.network.response.MemberResponse;
import com.itschool.springbootdeveloper.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Service 계층 : 비즈니스 로직 처리와 비즈니스 관련된 도메인 모델의 적합성 검증
// 트랜잰션 관리 및 처리
@Service
@RequiredArgsConstructor
public class MemberService {
    
    // Service 계층 <-> Persistence 계층
    private final MemberRepository memberRepository;

    public MemberResponse response(Member member) {
        return new MemberResponse(member);
    }

    public List<MemberResponse> responseList(List<Member> articleList) {
        return articleList.stream()
                .map(MemberResponse::new)
                .toList();
    }

    // 블로그 글 추가 메서드
    // DTO : Data Transfer Object
    public MemberResponse create(MemberRequest request) { // 영속성 계층 객체, request 객체
        return response(memberRepository.save(request.toEntity())); // response 객체
    }

    public MemberResponse read(Long id) {
        return response(memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음")));
    }

    public List<MemberResponse> readAll() {
        return responseList(memberRepository.findAll());
    }

    @Transactional
    public MemberResponse update(Long id, MemberRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        member.update(request); // 변경된 이후 dirty checking

        // memberRepository.save()를 호출해야 하지 않나? @Transactional으로 하나의 트랜잭션으로 메서드 리턴되기 전에 commit() 호출(update 쿼리)
        // return response(memberRepository.save(member));

        return response(member);
    }

    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        memberRepository.deleteById(id);
    }
    
    /*public List<Member> getAllMembers() {
        return memberRepository.findAll(); // 멤버 목록 얻기
    }

    public Member findMemberById(Long id) {
        return memberRepository.findById(id) // 찾으면 Member 객체 리턴
                .orElseThrow(() -> new EntityNotFoundException()); // 못 찾으면 예외 발생
    }

    public List<Member> findMembersByName(String name) {
        return memberRepository.findByName(name);
    }

    @Transactional // 트랜잭션 처리
    public void test() {
        // JPA(인터페이스), Hibernate(구현체)를 개발자가 직접 쓰게 되면 엔티티를 직접 관리하고 커밋해야 하는 등
        // 신경쓸 부분이 많아 직접 사용하지 않아도 스프링 데이터가 데이터베이스 사용 기능을 클래스 레벨 추상화
        // JpaRepository 인터페이스에는 CRUD를 포함한 여러 메서드가 포함되고, 알아서 쿼리를 만들어줌
        // 덕분에 비즈니스 로직에 더 집중할 수 있음

        // 1. 생성
        Member member = new Member("A");
        memberRepository.save(member); // 기존에 존재하는지 확인, 없으면 Create, 있으면 Update
        // IDENTITY 전략은 실행 시점에 즉시 INSERT SQL이 실행됨, 성능 상의 이점을 볼 수 없음
        
        // 2. 조회
        Optional<Member> member2 = memberRepository.findById(1L); // 단건 조회
        List<Member> allMember = memberRepository.findAll();
        
        // 3. 삭제
        memberRepository.deleteById(1L);
    }*/
}