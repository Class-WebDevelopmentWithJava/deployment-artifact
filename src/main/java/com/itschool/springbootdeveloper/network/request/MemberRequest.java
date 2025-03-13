package com.itschool.springbootdeveloper.network.request;

import com.itschool.springbootdeveloper.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MemberRequest {
    private String name;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .build();
    }
}
