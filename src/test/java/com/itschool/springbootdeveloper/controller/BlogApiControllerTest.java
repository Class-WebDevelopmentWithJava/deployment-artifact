package com.itschool.springbootdeveloper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.springbootdeveloper.MockMvcTest;
import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import com.itschool.springbootdeveloper.network.request.MemberRequest;
import com.itschool.springbootdeveloper.repository.BlogRepository;
import com.itschool.springbootdeveloper.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

class BlogApiControllerTest extends MockMvcTest {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper; // 직렬화와 역직렬화를 위한 클래스
    
    @BeforeEach
    public void BeforeCleanUp() {
        blogRepository.deleteAll(); // 블로그 글 전부 삭제

        memberRepository.deleteAll(); // 멤버 전부 삭제
        memberRepository.save(Member.builder() // 멤버 생성
                .name("테스트 유저")
                .build());
    }
    
    @DisplayName("create Article : 블로그 글 추가에 성공한다.")
    @Test
    public void create() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        Member savedMember = memberRepository.getFirstByOrderByIdDesc()
                .orElseThrow(()-> new EntityNotFoundException());

        MemberRequest memberRequest = MemberRequest.builder()
                .id(savedMember.getId())
                .build();

        final ArticleRequest articleRequest = ArticleRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .author(memberRequest)
                .build();

        // 객체를 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(articleRequest);

        // when : 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then : 검증
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("테스트 제목"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("테스트 내용"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id").value(savedMember.getId()));

        assertThat(blogRepository.findAll().size()).isEqualTo(1);;
    }

    @DisplayName("read Article : 블로그 글 조회에 성공한다.")
    @Test
    public void read() throws Exception {
        // given
        final String title = "read 테스트";
        final String content = "read 테스트";

        Member savedMember = memberRepository.getFirstByOrderByIdDesc()
                .orElseThrow(()-> new EntityNotFoundException());

        Article article = Article.builder()
                .title(title)
                .content(content)
                .author(savedMember)
                .build();
        
        Article newArticle = blogRepository.save(article);

        final String url = "/api/articles/" + newArticle.getId();

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk()) // 응답이 OK(200) 코드인지 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(content))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.id").value(savedMember.getId()));

        assertThat(blogRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("update Article : 블로그 글 수정에 성공한다.(외래키 변경이 아닌 필드 변경을 검증)")
    @Test
    public void update() throws Exception {
        // given
        final String title = "update test";
        final String content = "update test";
        
        Member savedMember = memberRepository.getFirstByOrderByIdDesc()
                .orElseThrow(()-> new EntityNotFoundException());

        Article savedArticle = blogRepository.save(Article.builder()
                                            .title("업데이트 되기 전")
                                            .content("업데이트 되기 전")
                                            .author(savedMember)
                                            .build());


        final String url = "/api/articles/" + savedArticle.getId();

        ArticleRequest articleRequest = ArticleRequest.builder()
                .title(title)
                .content(content)
                .build();

        // 객체를 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(articleRequest);

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk()) // 응답이 OK(200) 코드인지 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(content));

        assertThat(blogRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("delete Article : 블로그 글 삭제에 성공한다.")
    @Test
    public void delete() throws Exception {
        // given
        Member savedMember = memberRepository.getFirstByOrderByIdDesc()
                .orElseThrow(()-> new EntityNotFoundException());

        Article savedArticle = blogRepository.save(Article.builder()
                .title("삭제될 데이터")
                .content("삭제될 데이터")
                .author(savedMember)
                .build());

        final String url = "/api/articles/" + savedArticle.getId();

        // when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .accept(MediaType.APPLICATION_JSON));;

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk()); // 응답이 OK(200) 코드인지 확인

        assertThat(blogRepository.findAll().size()).isEqualTo(0);
    }

    @DisplayName("read All: 블로그 글 전체 조회에 성공한다.")
    @Test
    public void readAll() throws Exception {
        // given : 테스트 실행 준비
        final String url = "/api/articles";
        final String title = "전체 조회될 데이터";
        final String content = "전체 조회될 데이터";


        Member savedMember = memberRepository.getFirstByOrderByIdDesc()
                .orElseThrow(()-> new EntityNotFoundException());

        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .author(savedMember)
                .build());

        // when : 테스트 진행
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON));

        // then : 결과 검증
        Long index = memberRepository.count() - 1;

        result.andExpect(MockMvcResultMatchers.status().isOk()) // 응답이 OK(200) 코드인지 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$[" + index + "].title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$[" + index + "].content").value(content));

        assertThat(blogRepository.findAll().size()).isEqualTo(1);
    }
}