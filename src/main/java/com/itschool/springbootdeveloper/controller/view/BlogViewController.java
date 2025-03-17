package com.itschool.springbootdeveloper.controller.view;

import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.network.response.ArticleResponse;
import com.itschool.springbootdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleResponse> articles =  blogService.readAll().getBody();
        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        ArticleResponse article = blogService.read(id).getBody();

        model.addAttribute("article", article);

        return "article";
    }

    @GetMapping("/new-article")
    // GET URL에 쿼리 형태로 추가 /new-article, /new-article?id=2
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if(id == null) { // 글 등록
            model.addAttribute("article", new ArticleResponse());
        } else { // 글을 수정
            ArticleResponse response = blogService.read(id).getBody();
            model.addAttribute("article", response);
        }

        return "newArticle"; // newArticle.html이라는 파일 templates 폴더에서 찾음
    }
}
