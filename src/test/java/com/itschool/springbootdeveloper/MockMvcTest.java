package com.itschool.springbootdeveloper;

import com.itschool.springbootdeveloper.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// Mockito : 테스트에 사용할 가짜 객체인 Mock 객체를 만들고, 관리하고, 검증할 수 있는 프레임워크
@AutoConfigureMockMvc // Mock MVC 생성 및 자동 구성
// MockMvc + @SpringBootTest + @AutoConfigureMockMvc
// MockMvc를 사용하지만, 전체 Spring Context를 로드하여 테스트. 실제 서비스 및 DB를 포함 → 통합 테스트에 가까움
public abstract class MockMvcTest extends SpringBootDeveloperApplicationTest {

    @Autowired
    private WebApplicationContext context;

    // 모의 객체(Mock Object)란 실제 사용하는 모듈을 사용하지 않고
    // 실제의 모듈을 "흉내"내는 "가짜" 모듈을 작성하여 테스트의 효용성을 높이는 데 사용하는 객체
    // HTTP 요청을 모의(Mock)하여 @Controller 또는 @RestController의 동작을 검증
    protected MockMvc mockMvc; // Spring MVC 컨트롤러를 테스트하는 데 사용, 실제 서버를 띄우지 않고 가짜 요청을 만들어 컨트롤러를 호출

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build(); // mockMvc에서 상태 값을 가지고 있어 초기화
        memberRepository.deleteAll();
        System.out.println(this.getClass().getName() + " 테이블 전부 delete");
    }
}
