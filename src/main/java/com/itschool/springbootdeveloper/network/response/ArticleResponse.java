package com.itschool.springbootdeveloper.network.response;

import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleResponse {

    private Long id;

    private String title;

    private String content;

    private MemberResponse author;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = new MemberResponse(article.getAuthor()); // getAuthor() 호출 시 select 쿼리 생성
    }
}
