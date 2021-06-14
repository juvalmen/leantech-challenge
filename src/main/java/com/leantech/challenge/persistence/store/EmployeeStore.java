package com.leantech.challenge.persistence.store;

import com.leantech.challenge.persistence.repository.EmployeeRepository;
import com.leantech.challenge.pojo.api.EmployeeTO;
import com.leantech.challenge.pojo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeStore extends Store<Employee, EmployeeTO> {

    @Autowired
    private EmployeeRepository repository;

    @Override
    protected EmployeeRepository getRepository() {
        return repository;
    }

    @Override
    protected Class<Employee> entityClass() {
        return Employee.class;
    }

    @Override
    protected Class<EmployeeTO> dtoClass() {
        return EmployeeTO.class;
    }

    public List<EmployeeTO> findAll(final String position, final String employeeName) {
        return mapper.map(repository.findEmployeesByPositionAndName(position, employeeName), EmployeeTO.class);
    }

    public List<EmployeeTO> findByPositionIdOrderBySalaryDesc(final Long positionId) {
        return mapper.map(repository.findByPositionIdOrderBySalaryDesc(positionId), EmployeeTO.class);
    }

}
