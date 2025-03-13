package com.itschool.springbootdeveloper.domain;

import com.itschool.springbootdeveloper.domain.base.BaseEntity;
import com.itschool.springbootdeveloper.network.request.ArticleRequest;
import com.itschool.springbootdeveloper.network.request.MemberRequest;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자, (JPA 지연로딩) 프록시 조회 접근을 위해 protected 또는 public 
@AllArgsConstructor // 모든 필드를 파라미터로 가진 생성자
@Getter // 모든 필드의 getter 자동 생성
@Setter
@Builder
@Entity/*(name = "users")*/ // Member 객체를 JPA가 관리하는 엔티티로 지정, 속성 name 사용하면 테이블명을 매핑해줄 수 있음
// 엔티티는 데이터베이스의 테이블과 매핑되는 객체를 의미함
// 엔티티는 본질적으로는 자바 객체이므로 일반 객체와 다르지 않음
// 데이터베이스의 테이블과 직접 연결된다는 특별한 특징이 있음
// 엔티티 매니저 : 엔티티 매니저는 엔티티를 관리해 데이터베이스와 어플리케이션 사이에서 객체를 생성, 수정, 삭제하는 등의 역할을 함
// 엔티티 매니저는 Spring Data JPA에서 관리하므로 직접 생성하거나 관리할 필요가 없음
// 엔티티 매니저 팩토리 : 엔티티 매니저를 생성, 어플리케이션 내에서 단 하나만 존재
public class Member extends BaseEntity<MemberRequest> {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에 위임
    // AUTO: 선택한 데이터베이스 방언(dialect)에 따라 방식을 자동으로 선택(기본값). 참고로 postgres는 auto를 하면 sequence 생성 방식
    // SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본키를 할당하는 방법
    // TABLE: 키 생성 테이블 사용
    @Column(name = "id", updatable = false) // name 속성으로 DB 실제 컬럼명 기재, name이 없으면 변수명을 lower_snake_case로 변경
    private Long id; // 변수 id는 DB 테이블의 'id' 컬럼과 매핑

    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT ''") // DB의 name이라는 컬럼과 매핑
    // name : 필드와 매핑할 컬럼이름, 설정하지 않으면 필드 이름으로 지정해줌
    // nullable : 컬럼의 null 허용 여부, 설정하지 않으면 true(nullable), false(NOT NULL)
    // unique : 컬럼의 유일한 값(unique 제약조건) 여부, 설정하지 않으면 false(non unique)
    // columnDefinition : 컬럼 정보 설정, DDL을 직접 정의하고 싶을때
    private String name;

    public Member(String name) {
        this.name = name;
    }

    public void update(MemberRequest request) {
        this.name = request.getName();
    }
}
