package com.leantech.challenge.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.EAGER;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "employees")
public class Employee extends BaseEntity {

    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_employee_to_person"), nullable = false)
    @OneToOne(cascade = MERGE, fetch = EAGER)
    private Person person;

    @JoinColumn(name = "position_id", foreignKey = @ForeignKey(name = "fk_employee_to_position"), nullable = false)
    @OneToOne(cascade = MERGE, fetch = EAGER)
    private Position position;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

}
