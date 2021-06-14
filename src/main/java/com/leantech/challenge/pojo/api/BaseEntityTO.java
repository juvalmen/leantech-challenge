package com.leantech.challenge.pojo.api;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public class BaseEntityTO {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;

}
