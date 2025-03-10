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
        memberRepository.deleteAll();
        System.out.println(this.getClass().getName() + " 테이블 전부 delete");
    }

    @AfterEach // 테스트 실행 후 실행하는 메서드
    public void AfterCleanUp() {
        memberRepository.deleteAll();
        System.out.println(this.getClass().getName() + " 테이블 전부 delete");
    }

    // region CRUD
    @Test
    @Transactional
    void create() {

        try {
            Member entity = Member.builder()
                    .id(1L)
                    .name("abc")
                    .build();

            Member newEntity = memberRepository.save(entity);

            assertThat(entity.getName()).isEqualTo(newEntity.getName());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void read() {

        Long id = 1L;
        String name = "test";

        try {
            Member entity = Member.builder()
                    .id(id)
                    .name(name)
                    .build();

            Member newEntity = memberRepository.save(entity);

            Optional<Member> selectedEntity = memberRepository.findById(id);

            assertThat(selectedEntity.get()).isNotNull();

            selectedEntity.ifPresent(e -> {
                System.out.println("Result : " + e);
            });

            assertThat(entity.getId()).isEqualTo(newEntity.getId());
            assertThat(entity.getName()).isEqualTo(newEntity.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void update() {

        try {

            Optional<Member> maxRow = memberRepository.getFirstByOrderByIdDesc();

            Member entity = maxRow.get();

            maxRow.ifPresent(perform -> {
                entity.setName("change");
            });

            Member newEntity = memberRepository.save(entity);

            assertThat(entity.getId()).isEqualTo(newEntity.getId());
            assertThat(entity.getName()).isEqualTo(newEntity.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void delete() {
        Optional<Long> before = memberRepository.countBy();
        Long beforeCount = before.get();

        try {
            Optional<Member> maxRow = memberRepository.getFirstByOrderByIdDesc();

            memberRepository.delete(maxRow.get());

            Optional<Long> after = memberRepository.countBy();
            Long afterCount = after.get();

            assertThat(afterCount + 1L).isEqualTo(beforeCount);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}