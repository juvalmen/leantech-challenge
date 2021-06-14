package com.leantech.challenge.service.employee;

import com.leantech.challenge.persistence.store.EmployeeStore;
import com.leantech.challenge.pojo.api.EmployeeSearchTO;
import com.leantech.challenge.pojo.api.EmployeeTO;
import com.leantech.challenge.service.person.PersonService;
import com.leantech.challenge.service.position.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.leantech.challenge.utils.ValidateUtils.checkArgument;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeStore employeeStore;

    @Autowired
    private PersonService personService;

    @Autowired
    private PositionService positionService;

    public EmployeeTO save(final EmployeeTO to) {
        final EmployeeTO employeeTO = EmployeeTO.builder()
                .personTO(personService.findById(to.getPersonTO()))
                .positionTO(positionService.findById(to.getPositionTO()))
                .salary(to.getSalary())
                .build();
        employeeTO.setId(to.getId());
        final EmployeeTO employeeTOSaved = employeeStore.save(employeeTO);
        log.info("Employee created successfully");
        return employeeTOSaved;
    }

    public EmployeeTO update(final EmployeeTO to) {
        final EmployeeTO employeeTO = this.findById(to);
        employeeTO.setPersonTO(personService.findById(to.getPersonTO()));
        employeeTO.setPositionTO(positionService.findById(to.getPositionTO()));
        employeeTO.setSalary(to.getSalary());
        final EmployeeTO employeeTOUpdated = employeeStore.update(employeeTO);
        log.info("Employee updated successfully");
        return employeeTOUpdated;
    }

    public EmployeeTO findById(final EmployeeTO to) {
        checkArgument(nonNull(to), "EmployeeTO");
        final EmployeeTO employeeTO = employeeStore.findById(to.getId());
        log.info("Employee found successfully");
        return employeeTO;
    }

    public void delete(final Long id) {
        final EmployeeTO employeeTO = employeeStore.findById(id);
        employeeStore.delete(employeeTO);
        log.info("Employee deleted successfully");
    }

    public List<EmployeeTO> findAll(final String position, final String employeeName) {
        final List<EmployeeTO> employeeTOS = employeeStore.findAll(position, employeeName);
        return employeeTOS;
    }

    public List<EmployeeSearchTO> findByPositionIdOrderBySalaryDesc(final Long positionId) {
        final List<EmployeeTO> employeeTOS = employeeStore.findByPositionIdOrderBySalaryDesc(positionId);
        final List<EmployeeSearchTO> employeeSearchTOS = newArrayList();
        employeeTOS.stream().forEach((e) -> {
            employeeSearchTOS.add(EmployeeSearchTO.builder()
                    .id(e.getId())
                    .salary(e.getSalary())
                    .person(e.getPersonTO()).build());
        });
        return employeeSearchTOS;
    }
}
