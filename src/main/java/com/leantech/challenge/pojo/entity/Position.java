package com.leantech.challenge.pojo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "positions")
public class Position extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

}
