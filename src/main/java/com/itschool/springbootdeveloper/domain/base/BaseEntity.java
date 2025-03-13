package com.itschool.springbootdeveloper.domain.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseEntity <Req> {
    public abstract void update(Req RequestEntity);
}
