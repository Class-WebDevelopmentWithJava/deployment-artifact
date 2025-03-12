package com.itschool.springbootdeveloper.network.response;

import com.itschool.springbootdeveloper.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberResponse {
    private Long id;

    private String name;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = getName();
    }
}
