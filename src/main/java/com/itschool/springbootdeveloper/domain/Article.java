package com.itschool.springbootdeveloper.domain;

import com.itschool.springbootdeveloper.domain.base.BaseEntity;
import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Article extends BaseEntity<ArticleRequest> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false) // NOT NULL
    private String title;

    @Column(nullable = false) // NOT NULL
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false) // NOT NULL, 게시글에는 무조건 작성자가 있어야 한다.
    private Member author;

    public void update(ArticleRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
    }
}
