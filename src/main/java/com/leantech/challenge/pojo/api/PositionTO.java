package com.leantech.challenge.pojo.api;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PositionTO extends BaseEntityTO {

    @NotBlank(message="Param name is mandatory")
    private String name;

}
