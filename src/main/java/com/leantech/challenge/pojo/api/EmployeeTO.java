package com.leantech.challenge.pojo.api;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTO extends BaseEntityTO {

    @NotNull(message="Param person is mandatory")
    private PersonTO personTO;

    @NotNull(message="Param position is mandatory")
    private PositionTO positionTO;

    @NotNull(message="Param salary is mandatory")
    @Min(value = 1, message = "Salary must be greather than 1")
    private BigDecimal salary;

}
