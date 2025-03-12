package com.itschool.springbootdeveloper.service;

import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import com.itschool.springbootdeveloper.network.response.ArticleResponse;
import com.itschool.springbootdeveloper.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public ArticleResponse response(Article article) {
        return new ArticleResponse(article);
    }

    public List<ArticleResponse> responseList(List<Article> articleList) {
        return articleList.stream()
                .map(ArticleResponse::new)
                .toList();
    }

    // 블로그 글 추가 메서드
    // DTO : Data Transfer Object
    public ArticleResponse create(ArticleRequest request) { // 영속성 계층 객체, request 객체
        return response(blogRepository.save(request.toEntity())); // response 객체
    }

    public ArticleResponse read(Long id) {
        return response(blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음")));
    }

    public List<ArticleResponse> readAll() {
        return responseList(blogRepository.findAll());
    }

    @Transactional
    public ArticleResponse update(Long id, ArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        article.update(request); // 변경된 이후 dirty checking

        // blogRepository.save()를 호출해야 하지 않나? @Transactional으로 하나의 트랜잭션으로 메서드 리턴되기 전에 commit() 호출(update 쿼리)
        // return response(blogRepository.save(article));

        return response(article);
    }

    public void delete(Long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        blogRepository.deleteById(id);
    }
}
