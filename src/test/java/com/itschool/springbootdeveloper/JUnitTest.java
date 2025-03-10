package com.itschool.springbootdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
public class JUnitTest {

    @DisplayName("1 + 2는 3이다")
    @Test
    public void junitTest() {
        int a = 1;
        int b = 2;
        int sum = 3;

        assertThat(a+b).isEqualTo(sum);
    }

    @DisplayName("1 + 3는 4이다")
    @Test
    public void junitFailedTest() {
        int a = 1;
        int b = 3;
        int sum = 3;

        assertThat(a+b).isEqualTo(sum);
    }
}
