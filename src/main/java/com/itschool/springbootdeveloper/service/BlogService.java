package com.itschool.springbootdeveloper.service;

import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import com.itschool.springbootdeveloper.network.response.ArticleResponse;
import com.itschool.springbootdeveloper.repository.BlogRepository;
import com.itschool.springbootdeveloper.repository.MemberRepository;
import com.itschool.springbootdeveloper.service.base.CrudService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService extends CrudService<ArticleRequest, ArticleResponse, Article> {

    private final MemberRepository memberRepository;

    @Autowired
    public BlogService(BlogRepository baseRepository,
                       MemberRepository memberRepository) {
        super(baseRepository);
        this.memberRepository = memberRepository;
    }

    @Override
    protected Article convertBaseEntityFromRequest(ArticleRequest requestEntity) {
        Member member = memberRepository.findById(requestEntity.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException());

        return Article.builder()
                .title(requestEntity.getTitle())
                .content(requestEntity.getContent())
                .author(member)
                .build();
    }

    @Override
    protected ArticleResponse response(Article entity) {
        return new ArticleResponse(entity);
    }

    @Override
    protected BlogRepository getBaseRepository() {
        return (BlogRepository) baseRepository;
    }

    // Update 예시
    /*@Override
    @Transactional
    public ResponseEntity<ArticleResponse> update(Long id, ArticleRequest request) {
        Article entity = baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));
        
        Member findMember = memberRepository.findById(request.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(request);

        return ResponseEntity.ok(response(entity));
    }*/
}
