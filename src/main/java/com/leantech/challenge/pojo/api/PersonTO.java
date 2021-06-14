package com.leantech.challenge.pojo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class PersonTO extends BaseEntityTO {

    @NotBlank(message="Param name is mandatory")
    private String name;
    @NotBlank(message="Param last name is mandatory")
    @JsonProperty("lastName")
    private String lastName;
    @NotBlank(message="Param address is mandatory")
    private String address;
    private String cellphone;
    @NotBlank(message="Param city name is mandatory")
    private String cityName;

}
