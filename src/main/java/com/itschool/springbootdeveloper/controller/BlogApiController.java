package com.itschool.springbootdeveloper.controller;

import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import com.itschool.springbootdeveloper.network.response.ArticleResponse;
import com.itschool.springbootdeveloper.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
@RequestMapping("/api/articles")
public class BlogApiController {
    private final BlogService blogService;

    @GetMapping("{id}")
    public ResponseEntity<ArticleResponse> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.read(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ArticleResponse>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(blogService.readAll());
    }

    @PostMapping("")
    public ResponseEntity<ArticleResponse> create(@RequestBody ArticleRequest request) {
        // 200 OK, 201 Created, 400 Bad Request...
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(blogService.create(request));
    }

    // update http method? put(전체 수정), patch(부분 수정), patch는 이 과정에서는 생략
    @PutMapping("{id}")
    public ResponseEntity<ArticleResponse> update(@PathVariable Long id,
                                                  @RequestBody ArticleRequest request) {
        ArticleResponse response = blogService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    // delete http method? delete(단건 삭제)
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        blogService.delete(id); // return 값 확인
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
