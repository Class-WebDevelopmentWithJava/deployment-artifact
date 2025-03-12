package com.itschool.springbootdeveloper.repository;

import com.itschool.springbootdeveloper.domain.Article;
import com.itschool.springbootdeveloper.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Article, Long> {
    Optional<Article> findById(Long id);

    Optional<Member> getFirstByOrderByIdDesc();

    Optional<Long> countBy();
}
