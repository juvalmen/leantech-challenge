package com.leantech.challenge.persistence.repository;

import com.leantech.challenge.pojo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query(value = "SELECT e.* " +
            "FROM Employees e " +
            "JOIN Positions po ON e.position_id = po.id " +
            "JOIN Persons pe ON e.position_id = pe.id " +
            "WHERE (:position is null or po.name like %:position%) and (:employeeName is null or pe.name like %:employeeName%)", nativeQuery = true)
    List<Employee> findEmployeesByPositionAndName(@Param("position") final String position, @Param("employeeName") final String employeeName);

    List<Employee> findByPositionIdOrderBySalaryDesc(final Long positionId);

}
