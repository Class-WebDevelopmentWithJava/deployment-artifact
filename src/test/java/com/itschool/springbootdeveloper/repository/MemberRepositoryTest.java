package com.itschool.springbootdeveloper.repository;

import com.itschool.springbootdeveloper.SpringBootDeveloperApplicationTest;
import com.itschool.springbootdeveloper.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// 레포지토리 단위 테스트 작성 예시
class MemberRepositoryTest extends SpringBootDeveloperApplicationTest {
    // Dependency Injection (DI)
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void BeforeCleanUp() {
        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
        memberRepository.deleteAll(); // select 쿼리 날린 후 Hibernate 내부에서 개별 삭제
        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
    }

    @AfterEach // 테스트 실행 후 실행하는 메서드
    public void AfterCleanUp() {
        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
        memberRepository.deleteAll(); // select 쿼리 날린 후 Hibernate 내부에서 개별 삭제
        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
    }

    // region CRUD
    @Test
    void create() {
        Member entity = Member.builder() // 멤버 객체 생성
                .name("abc")
                .build();

        Member newEntity = memberRepository.save(entity); // 새로운 객체 저장
        
        System.out.println("entity의 id값 : " + entity.getId()); // entity도 마찬가지로 영속상태로 등록
        System.out.println("newEntity의 id값 : " + newEntity.getId()); // 영속성 컨텍스트에서 관리
        System.out.println(entity == newEntity); // 결과값 true, 영속성 컨텍스트가 같은 엔티티는 같은 주소값을 반환

        assertThat(entity.getName()).isEqualTo(newEntity.getName()); // 이름만 비교해서 확인
    }

    @Test
    // @Transactional
    void read() {
        String name = "test";

        Member entity = Member.builder() // 멤버 객체 생성
                .name(name)
                .build();

        Member newEntity = memberRepository.save(entity); // 영속상태로 등록되어 newEntity == entity는 같음

        Member findEntity = memberRepository.findById(newEntity.getId()) // newEntity의 id값을 가지고 찾기
                .orElseThrow(() -> new RuntimeException());

        // 1차 캐시는 이미 조회한 데이터가 2번 조회되지 않도록 내부적으로 객체를 관리
        System.out.println(newEntity == findEntity); // 같은 트랜잭션 안에서는 true, 다른 트랜잭션에서는 false

        assertThat(entity.getId()).isEqualTo(findEntity.getId());
        assertThat(entity.getName()).isEqualTo(findEntity.getName());
    }

    @Test
    // @Transactional // 한 트랜잭션으로 묶음
    void update() {

        String name = "test";

        Member entity = Member.builder() // 객체 생성
                .name(name)
                .build();

        memberRepository.save(entity); // 객체를 저장

        Member maxRow = memberRepository.getFirstByOrderByIdDesc()
                .orElseThrow(() -> new RuntimeException("No member found")); // 하나의 트랜잭션 안에서 RuntimeException이 발생하면 rollback;

        maxRow.setName("change");
        memberRepository.save(maxRow); // save를 통해 update, 만약에 하나의 트랜잭션으로 묶으면 save 호출 필요 없음
        // JPA에서 컬렉션에서 꺼내쓰는 것처럼 구현했기때문에 같은 트랜잭션 내에서는 save를 하지 않아도 자동 저장

        assertThat(maxRow.getName()).isEqualTo("change");

    }

    @Test
    void delete() {
        long beforeCount = memberRepository.count(); // 0개

        Member entity = Member.builder()
                .name("delete test")
                .build();

        memberRepository.save(entity); // 객체 저장

        Optional<Member> maxRow = memberRepository.getFirstByOrderByIdDesc(); // 마지막에 저장한 것을 조회

        maxRow.ifPresent(memberRepository::delete); // 해당 조회가 성공했으면 해당 객체를 삭제

        long afterCount = memberRepository.count(); // 0개

        assertThat(afterCount).isEqualTo(beforeCount); // 0개 == 0개, true

    }
}