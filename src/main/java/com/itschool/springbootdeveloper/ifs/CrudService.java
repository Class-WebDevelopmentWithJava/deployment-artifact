package com.itschool.springbootdeveloper.ifs;

import java.util.List;

public interface CrudService<Req, Res> {

    Res create(Req request);

    Res read(Long id);

    List<Res> readAll();

    // 게시판의 글을 조회하듯이 해당 사이즈의 해당 페이지를 조회
    // ResponseEntity<List<Res>> readPaginatedList(Pageable pageable);
    
    Res update(Long id, Req request);

    void delete(Long id);
}
