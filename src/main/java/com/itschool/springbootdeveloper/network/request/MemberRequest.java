package com.itschool.springbootdeveloper.network.request;

import com.itschool.springbootdeveloper.domain.Member;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class MemberRequest {

    private Long id;

    private String name;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .build();
    }
}
