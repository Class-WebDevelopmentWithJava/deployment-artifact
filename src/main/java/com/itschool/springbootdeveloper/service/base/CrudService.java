package com.itschool.springbootdeveloper.service.base;

import com.itschool.springbootdeveloper.domain.base.BaseEntity;
import com.itschool.springbootdeveloper.ifs.CrudInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class CrudService <Req, Res, Entity extends BaseEntity> implements CrudInterface<Req, Res> {

    protected final JpaRepository<Entity, Long> baseRepository;

    // 요청을 엔티티로 변경
    protected abstract Entity convertBaseEntityFromRequest(Req requestEntity);

    // 엔티티를 응답으로 변경
    protected abstract Res response(Entity entity);

    protected abstract JpaRepository<Entity, Long> getBaseRepository();

    // 엔티티 리스트를 응답 리스트로 변경
    public List<Res> responseList(List<Entity> articleList) {
        return articleList.stream()
                .map((entity) -> response(entity))
                .toList();
    }

    // Create
    public ResponseEntity<Res> create(Req request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response(baseRepository.save(convertBaseEntityFromRequest(request))));
    }

    // Read
    public ResponseEntity<Res> read(Long id) {
        return ResponseEntity.ok(response(baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"))));
    }

    public ResponseEntity<List<Res>> readAll() {
        return ResponseEntity.ok(responseList(baseRepository.findAll()));
    }

    // Update
    @Transactional
    public ResponseEntity<Res> update(Long id, Req request) {
        Entity entity = baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        entity.update(request);

        return ResponseEntity.ok(response(entity));
    }

    public ResponseEntity delete(Long id) {
        Entity entity = baseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id로 해당 객체 못 찾음"));

        baseRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
