package com.itschool.springbootdeveloper.network.request;

import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.domain.Member;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class ArticleRequest {

    private Long id;

    private String title;

    private String content;

    private MemberRequest author;

    /*public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }*/
}