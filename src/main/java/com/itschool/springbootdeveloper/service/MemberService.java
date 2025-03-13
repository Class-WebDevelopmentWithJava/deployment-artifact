package com.itschool.springbootdeveloper.service;

import com.itschool.springbootdeveloper.domain.Member;
import com.itschool.springbootdeveloper.network.request.MemberRequest;
import com.itschool.springbootdeveloper.network.response.MemberResponse;
import com.itschool.springbootdeveloper.repository.MemberRepository;
import com.itschool.springbootdeveloper.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Service 계층 : 비즈니스 로직 처리와 비즈니스 관련된 도메인 모델의 적합성 검증
// 트랜잰션 관리 및 처리
@Service
public class MemberService extends CrudService<MemberRequest, MemberResponse, Member> {

    public MemberService(JpaRepository<Member, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    protected Member convertBaseEntityFromRequest(MemberRequest requestEntity) {
        return requestEntity.toEntity();
    }

    @Override
    protected MemberResponse response(Member entity) {
        return new MemberResponse(entity);
    }
}