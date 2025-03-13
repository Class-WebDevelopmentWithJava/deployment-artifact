package com.itschool.springbootdeveloper.ifs;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudInterface <Req, Res> {

    ResponseEntity<Res> create(Req request);

    ResponseEntity<Res> read(Long id);

    ResponseEntity<List<Res>> readAll();

    // 게시판의 글을 조회하듯이 해당 사이즈의 해당 페이지를 조회
    // ResponseEntity<List<Res>> readPaginatedList(Pageable pageable);
    
    ResponseEntity<Res> update(Long id, Req request);

    ResponseEntity delete(Long id);
}
