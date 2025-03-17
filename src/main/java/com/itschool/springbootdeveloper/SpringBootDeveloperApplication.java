package com.itschool.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication // 스프링 부트 사용에 필요한 기본 설정이 포함됨
@EnableJpaAuditing // created_at, updated_at 자동 업데이트
public class SpringBootDeveloperApplication {
    public static void main(String[] args) { // 기존의 main 메서드(실행부), 스프링 부트 프로젝트 시작 지점
        // SpringApplication.run() 메서드는 어플리케이션 실행
        // 첫 번째 인수는 스프링 부트 어플리케이션의 메인 클래스로 사용할 클래스
        // 두 번째 인수는 커맨드 라인의 인수들을 전달
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
