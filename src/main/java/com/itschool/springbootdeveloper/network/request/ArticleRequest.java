package com.itschool.springbootdeveloper.network.request;

import com.itschool.springbootdeveloper.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleRequest {

    private String title;

    private String content;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}