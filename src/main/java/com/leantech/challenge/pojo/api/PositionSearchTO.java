package com.leantech.challenge.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionSearchTO {

    private Long id;
    private String name;
    private List<EmployeeSearchTO> employees;

}
