package com.itschool.springbootdeveloper.service;

import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Service 계층 : 비즈니스 로직 처리와 비즈니스 관련된 도메인 모델의 적합성 검증
// 트랜잰션 관리 및 처리
@Service
public class MemberService {
    
    // Service 계층 <-> Persistence 계층
    @Autowired // 빈 주입
    MemberRepository memberRepository;
    
    public List<Member> getAllMembers() {
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
    }
}