package com.leantech.challenge.persistence.store;

import com.leantech.challenge.persistence.repository.EmployeeRepository;
import com.leantech.challenge.pojo.api.EmployeeTO;
import com.leantech.challenge.pojo.entity.Employee;
import com.leantech.challenge.service.mapper.Mapper;
import org.jeasy.random.EasyRandom;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test
public class EmployeeStoreTest {

    private static final EasyRandom RANDOM = new EasyRandom();
    private final Employee entity = RANDOM.nextObject(Employee.class);
    private final EmployeeTO dto = RANDOM.nextObject(EmployeeTO.class);

    @InjectMocks
    private EmployeeStore target;
    @Mock
    private EmployeeRepository repository;
    @Mock
    private Mapper mapper;

    @BeforeMethod
    public void resetMocks() {
        reset(repository, mapper);
    }

    @BeforeTest
    public void init() {
        openMocks(this);
    }

    @Test
    public void findAllTest() {
        when(mapper.map(eq(newArrayList(entity)), eq(EmployeeTO.class))).thenReturn(newArrayList(dto));
        when(repository.findEmployeesByPositionAndName(Mockito.eq("pos"), Mockito.eq("name"))).thenReturn(newArrayList(entity));
        final List<EmployeeTO> employeeTOS = target.findAll(any(), any());
        assertNotNull(employeeTOS);
        verify(repository, times(1)).findEmployeesByPositionAndName(any(),any());
        verify(mapper, times(1)).map(any(), any());
    }

    @Test
    public void findByPositionIdOrderBySalaryDescTest() {
        when(mapper.map(eq(newArrayList(entity)), eq(EmployeeTO.class))).thenReturn(newArrayList(dto));
        when(repository.findByPositionIdOrderBySalaryDesc(Mockito.eq(1L))).thenReturn(newArrayList(entity));
        final List<EmployeeTO> employeeTOS = target.findByPositionIdOrderBySalaryDesc(any());
        assertNotNull(employeeTOS);
        verify(repository, times(1)).findByPositionIdOrderBySalaryDesc(any());
        verify(mapper, times(1)).map(any(), any());
    }

    @Test
    public void dtoClassTest() {
        assertNotNull(target.dtoClass());
        assertEquals(EmployeeTO.class, target.dtoClass());
    }

    @Test
    public void entityClassTest() {
        assertNotNull(target.entityClass());
        assertEquals(Employee.class, target.entityClass());
    }

    @Test
    public void getRepository() {
        assertEquals(repository, target.getRepository());
    }

}
