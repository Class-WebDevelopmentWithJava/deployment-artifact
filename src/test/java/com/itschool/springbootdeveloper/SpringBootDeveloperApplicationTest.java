package com.itschool.springbootdeveloper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) // JUnit 5에서 Spring의 테스트 컨텍스트를 로드할 수 있도록 확장
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 스프링 부트 애플리케이션을 테스트할 때 실제 애플리케이션 컨텍스트를 로드하며, 랜덤 포트에서 실행하여 웹 환경을 테스트
public abstract class SpringBootDeveloperApplicationTest {
    @Test
    void contextLoads() {
    }
}