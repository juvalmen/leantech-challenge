package com.leantech.challenge.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "persons")
public class Person extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "city_name", nullable = false)
    private String cityName;

}
