package com.itschool.springbootdeveloper.network.request;

import com.itschool.springbootdeveloper.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberRequest {
    private String name;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .build();
    }
}
