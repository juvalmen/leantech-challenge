package com.leantech.challenge.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSearchTO {

    private Long id;
    private BigDecimal salary;
    private PersonTO person;

}
