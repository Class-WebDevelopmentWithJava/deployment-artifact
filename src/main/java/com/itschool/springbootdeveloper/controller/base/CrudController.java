package com.itschool.springbootdeveloper.controller.base;

import com.itschool.springbootdeveloper.domain.base.BaseEntity;
import com.itschool.springbootdeveloper.ifs.CrudInterface;
import com.itschool.springbootdeveloper.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class CrudController <Req, Res, Entity extends BaseEntity>  implements CrudInterface<Req, Res> {

    protected final CrudService<Req, Res, Entity> baseService;

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected abstract CrudService<Req, Res, Entity> getBaseService();

    @PostMapping("")
    public ResponseEntity<Res> create(@RequestBody Req request) {
        log.info("create: {}에서 객체 {} 생성 요청", this.getClass().getSimpleName(), request);

        // 200 OK, 201 Created, 400 Bad Request...
        return baseService.create(request);
    }

    @GetMapping("{id}")
    public ResponseEntity<Res> read(@PathVariable Long id) {
        log.info("read: {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);

        return baseService.read(id);
    }

    @GetMapping("")
    public ResponseEntity<List<Res>> readAll() {
        log.info("readAll: {}에서 전체 조회 요청", this.getClass().getSimpleName());

        return baseService.readAll();
    }

    // update http method? put(전체 수정), patch(부분 수정), patch는 이 과정에서는 생략
    @PutMapping("{id}")
    public ResponseEntity<Res> update(@PathVariable Long id,
                                      @RequestBody Req request) {
        log.info("update: {}에서 id={}인 객체 {}로 수정 요청", this.getClass().getSimpleName(), id, request);

        return baseService.update(id, request);
    }

    // delete http method? delete(단건 삭제)
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        log.info("delete: {}에서 id={}인 객체 삭제 요청", this.getClass().getSimpleName(), id);

        return baseService.delete(id);
    }
}
