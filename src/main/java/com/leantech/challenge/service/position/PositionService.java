package com.leantech.challenge.service.position;

import com.leantech.challenge.persistence.store.PositionStore;
import com.leantech.challenge.pojo.api.PositionCustomSearchTO;
import com.leantech.challenge.pojo.api.PositionSearchTO;
import com.leantech.challenge.pojo.api.PositionTO;
import com.leantech.challenge.service.employee.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.leantech.challenge.utils.ValidateUtils.checkArgument;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class PositionService {

    @Autowired
    private PositionStore positionStore;

    @Autowired
    private EmployeeService employeeService;

    public PositionTO save(final PositionTO to) {
        final PositionTO positionTO = positionStore.save(to);
        log.info("Position created successfully");
        return positionTO;
    }

    public PositionTO findById(final PositionTO to) {
        checkArgument(nonNull(to), "PositionTO");
        final PositionTO positionTO = positionStore.findById(to.getId());
        log.info("Position found successfully");
        return positionTO;
    }

    public List<PositionTO> findAll() {
        final List<PositionTO> positionTOS = positionStore.findAll();
        log.info("Positions consulted successfully");
        return positionTOS;
    }

    public PositionCustomSearchTO findPositionsSortedByEmployeeSalary() {
        final List<PositionTO> positionTOs = this.findAll();
        final PositionCustomSearchTO positionCustomSearchTO = PositionCustomSearchTO.builder().positions(newArrayList()).build();
        positionTOs.stream().forEach((p) -> {
            final PositionSearchTO positionSearchTO = PositionSearchTO.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .employees(employeeService.findByPositionIdOrderBySalaryDesc(p.getId()))
                    .build();
            positionCustomSearchTO.getPositions().add(positionSearchTO);
        });
        log.info("Positions sorted by employee salary consulted successfully");
        return positionCustomSearchTO;
    }
}
