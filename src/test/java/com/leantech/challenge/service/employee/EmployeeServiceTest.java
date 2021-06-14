package com.leantech.challenge.service.employee;

import com.leantech.challenge.persistence.store.EmployeeStore;
import com.leantech.challenge.pojo.api.EmployeeSearchTO;
import com.leantech.challenge.pojo.api.EmployeeTO;
import com.leantech.challenge.pojo.api.PersonTO;
import com.leantech.challenge.pojo.api.PositionTO;
import com.leantech.challenge.service.person.PersonService;
import com.leantech.challenge.service.position.PositionService;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
public class EmployeeServiceTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    private EmployeeService target;

    @Mock
    private EmployeeStore employeeStore;

    @Mock
    private PersonService personService;

    @Mock
    private PositionService positionService;

    @BeforeClass
    public void beforeClass() {
        openMocks(this);
    }

    @BeforeMethod
    public void beforeMethod() {
        reset(employeeStore, personService, positionService);
    }

    public void saveTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        employeeTO.setId(null);
        final EmployeeTO expected = EASY_RANDOM.nextObject(EmployeeTO.class);
        when(employeeStore.save(any())).thenReturn(expected);
        final EmployeeTO actual = target.save(employeeTO);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).save(any(EmployeeTO.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void saveIllegalArgumentExceptionTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        employeeTO.setPersonTO(personTO);
        employeeTO.setPositionTO(positionTO);
        when(personService.findById(employeeTO.getPersonTO())).thenReturn(personTO);
        when(positionService.save(employeeTO.getPositionTO())).thenReturn(positionTO);
        when(employeeStore.save(any())).thenThrow(IllegalArgumentException.class);
        target.save(employeeTO);
        verify(employeeStore, times(2)).save(any(EmployeeTO.class));
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void saveEntityNotFoundExceptionTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        employeeTO.setPersonTO(personTO);
        employeeTO.setPositionTO(positionTO);
        when(personService.findById(employeeTO.getPersonTO())).thenThrow(EntityNotFoundException.class);
        when(positionService.save(employeeTO.getPositionTO())).thenReturn(positionTO);
        target.save(employeeTO);
        verify(employeeStore, times(0)).save(any(EmployeeTO.class));
    }

    @Test
    public void updateTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final EmployeeTO expected = EASY_RANDOM.nextObject(EmployeeTO.class);
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        when(target.findById(employeeTO)).thenReturn(employeeTO);
        when(personService.findById(employeeTO.getPersonTO())).thenReturn(personTO);
        when(positionService.findById(employeeTO.getPositionTO())).thenReturn(positionTO);
        when(employeeStore.update(any())).thenReturn(expected);
        final EmployeeTO actual = target.update(employeeTO);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).update(any(EmployeeTO.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateIllegalArgumentExceptionTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        employeeTO.setPersonTO(personTO);
        employeeTO.setPositionTO(positionTO);
        when(target.findById(employeeTO)).thenReturn(employeeTO);
        when(personService.findById(employeeTO.getPersonTO())).thenReturn(personTO);
        when(positionService.findById(employeeTO.getPositionTO())).thenReturn(positionTO);
        when(employeeStore.update(any())).thenThrow(IllegalArgumentException.class);
        target.update(employeeTO);
        verify(employeeStore, times(2)).update(any(EmployeeTO.class));
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void updateEntityNotFoundExceptionTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        final PositionTO positionTO = EASY_RANDOM.nextObject(PositionTO.class);
        employeeTO.setPersonTO(personTO);
        employeeTO.setPositionTO(positionTO);
        when(personService.findById(employeeTO.getPersonTO())).thenThrow(EntityNotFoundException.class);
        target.update(employeeTO);
        verify(employeeStore, times(0)).update(any(EmployeeTO.class));
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void findByIdEntityNotFoundExceptionTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final EmployeeTO expected = EASY_RANDOM.nextObject(EmployeeTO.class);
        when(employeeStore.findById(employeeTO.getId())).thenThrow(EntityNotFoundException.class);
        final EmployeeTO actual = target.findById(employeeTO);
        verify(employeeStore, times(2)).findById(any(Long.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdIllegalArgumentExceptionTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        employeeTO.setId(null);
        when(employeeStore.findById(null)).thenThrow(IllegalArgumentException.class);
        final EmployeeTO actual = target.findById(employeeTO);
        verify(employeeStore, times(2)).findById(null);
    }

    @Test
    public void findByIdTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final EmployeeTO expected = EASY_RANDOM.nextObject(EmployeeTO.class);
        when(employeeStore.findById(employeeTO.getId())).thenReturn(expected);
        final EmployeeTO actual = target.findById(employeeTO);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).findById(any(Long.class));
    }

    @Test
    public void deleteTest() {
        final EmployeeTO employeeTO = EASY_RANDOM.nextObject(EmployeeTO.class);
        final EmployeeTO expected = EASY_RANDOM.nextObject(EmployeeTO.class);
        doNothing().when(employeeStore).delete(any());
        when(employeeStore.findById(any())).thenReturn(expected);
        target.delete(expected.getId());
        verify(employeeStore, times(1)).findById(any(Long.class));
        verify(employeeStore, times(1)).delete(any(EmployeeTO.class));
    }

    @Test
    public void getAllTest() {
        final List<EmployeeTO> expected = newArrayList(EASY_RANDOM.nextObject(EmployeeTO.class));
        final String position = "Pos";
        final String employeeName = "Name";
        when(employeeStore.findAll(position, employeeName)).thenReturn(expected);
        final List<EmployeeTO> actual = target.findAll(position, employeeName);
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).findAll(position, employeeName);
    }

    @Test
    public void getAllPositionNullTest() {
        final List<EmployeeTO> expected = newArrayList(EASY_RANDOM.nextObject(EmployeeTO.class));
        final String employeeName = "Name";
        when(employeeStore.findAll(null, employeeName)).thenReturn(expected);
        final List<EmployeeTO> actual = target.findAll(null, employeeName);
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).findAll(null, employeeName);
    }

    @Test
    public void getAllEmployeeNullTest() {
        final List<EmployeeTO> expected = newArrayList(EASY_RANDOM.nextObject(EmployeeTO.class));
        final String position = "Pos";
        when(employeeStore.findAll(position, null)).thenReturn(expected);
        final List<EmployeeTO> actual = target.findAll(position, null);
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).findAll(position, null);
    }

    @Test
    public void getAllParamsNullTest() {
        final List<EmployeeTO> expected = newArrayList(EASY_RANDOM.nextObject(EmployeeTO.class));
        when(employeeStore.findAll(null, null)).thenReturn(expected);
        final List<EmployeeTO> actual = target.findAll(null, null);
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).findAll(null, null);
    }

    @Test
    public void findByPositionIdOrderBySalaryDesc() {
        final List<EmployeeSearchTO> expected = newArrayList(EASY_RANDOM.nextObject(EmployeeSearchTO.class));
        final List<EmployeeTO> employeeTOS = newArrayList(EASY_RANDOM.nextObject(EmployeeTO.class));
        expected.get(0).setId(employeeTOS.get(0).getId());
        expected.get(0).setSalary(employeeTOS.get(0).getSalary());
        expected.get(0).setPerson(employeeTOS.get(0).getPersonTO());
        when(employeeStore.findByPositionIdOrderBySalaryDesc(any(Long.class))).thenReturn(employeeTOS);
        final List<EmployeeSearchTO> actual = target.findByPositionIdOrderBySalaryDesc(1L);
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(employeeStore, times(1)).findByPositionIdOrderBySalaryDesc(1L);
    }

}
