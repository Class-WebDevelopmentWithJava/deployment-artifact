package com.itschool.springbootdeveloper.controller.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) {
        Person person = new Person();
        person.setId(1L);
        person.setName("홍길동");
        person.setAge(11);
        person.setHobbies(List.of("운동", "독서"));

        model.addAttribute("person", person);
        model.addAttribute("today", LocalDateTime.now());

        return "example"; // 디스패처 서블릿 + 뷰 리졸버 / 우리가 리턴한 값을 적절한 뷰로 배정
        // String + @ResponseBody로 return하면 text/html;
        // 객체 + @ResponseBody로 return하면 json으로 직렬화;
        // String으로 리턴하면 템플릿 해당 템플릿 뷰를 찾아서 리턴
    }

    @Getter
    @Setter
    static class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
