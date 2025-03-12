package com.itschool.springbootdeveloper.network.response;

import com.itschool.springbootdeveloper.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleResponse {

    private Long id;

    private String title;

    private String content;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
