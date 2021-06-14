package com.leantech.challenge.service.position;

import com.leantech.challenge.persistence.store.PositionStore;
import com.leantech.challenge.pojo.api.EmployeeSearchTO;
import com.leantech.challenge.pojo.api.PositionCustomSearchTO;
import com.leantech.challenge.pojo.api.PositionSearchTO;
import com.leantech.challenge.pojo.api.PositionTO;
import com.leantech.challenge.service.employee.EmployeeService;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test
public class PositionServiceTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    private PositionService target;

    @Mock
    private PositionStore positionStore;

    @Mock
    private EmployeeService employeeService;

    @BeforeClass
    public void beforeClass() {
        openMocks(this);
    }

    @Test
    public void saveTest() {
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        positionTO.setId(null);
        final PositionTO expected = EASY_RANDOM.nextObject(PositionTO.class);
        when(positionStore.save(positionTO)).thenReturn(expected);
        final PositionTO actual = target.save(positionTO);
        assertEquals(expected, actual);
        verify(positionStore, times(2)).save(any(PositionTO.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void saveIllegalArgumentExceptionTest() {
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        when(positionStore.save(positionTO)).thenThrow(IllegalArgumentException.class);
        final PositionTO actual = target.save(positionTO);
        verify(positionStore, times(1)).save(any(PositionTO.class));
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void findByIdEntityNotFoundExceptionTest() {
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        final PositionTO expected = EASY_RANDOM.nextObject(PositionTO.class);
        when(positionStore.findById(positionTO.getId())).thenThrow(EntityNotFoundException.class);
        final PositionTO actual = target.findById(positionTO);
        verify(positionStore, times(2)).findById(any(Long.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdIllegalArgumentExceptionTest() {
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        positionTO.setId(null);
        when(positionStore.findById(null)).thenThrow(IllegalArgumentException.class);
        final PositionTO actual = target.findById(positionTO);
        verify(positionStore, times(2)).findById(null);
    }

    @Test
    public void findByIdTest() {
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        final PositionTO expected = EASY_RANDOM.nextObject(PositionTO.class);
        when(positionStore.findById(positionTO.getId())).thenReturn(expected);
        final PositionTO actual = target.findById(positionTO);
        assertEquals(expected, actual);
        verify(positionStore, times(2)).findById(any(Long.class));
    }

    @Test
    public void getAllTest() {
        final List<PositionTO> expected = newArrayList(EASY_RANDOM.nextObject(PositionTO.class));
        when(positionStore.findAll()).thenReturn(expected);
        final List<PositionTO> actual = target.findAll();
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(positionStore, times(2)).findAll();
    }

    @Test
    public void findPositionsSortedByEmployeeSalaryTest() {
        final PositionCustomSearchTO expected = EASY_RANDOM.nextObject(PositionCustomSearchTO.class);
        final List<PositionTO> positionTOS = newArrayList(EASY_RANDOM.nextObject(PositionTO.class));
        final List<EmployeeSearchTO> employeeSearchTOS = newArrayList(EASY_RANDOM.nextObject(EmployeeSearchTO.class));
        final List<PositionSearchTO> positionSearchTOS = newArrayList(EASY_RANDOM.nextObject(PositionSearchTO.class));
        positionSearchTOS.get(0).setId(positionTOS.get(0).getId());
        positionSearchTOS.get(0).setName(positionTOS.get(0).getName());
        when(positionStore.findAll()).thenReturn(positionTOS);
        when(employeeService.findByPositionIdOrderBySalaryDesc(any(Long.class))).thenReturn(employeeSearchTOS);
        positionSearchTOS.get(0).setEmployees(employeeSearchTOS);
        expected.setPositions(positionSearchTOS);

        final PositionCustomSearchTO actual = target.findPositionsSortedByEmployeeSalary();
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(positionStore, times(1)).findAll();
    }

}
