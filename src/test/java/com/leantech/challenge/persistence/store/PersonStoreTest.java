package com.leantech.challenge.persistence.store;

import com.leantech.challenge.persistence.repository.PersonRepository;
import com.leantech.challenge.pojo.api.PersonTO;
import com.leantech.challenge.pojo.entity.Person;
import com.leantech.challenge.service.mapper.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Test
public class PersonStoreTest {

    @InjectMocks
    private PersonStore target;
    @Mock
    private PersonRepository repository;
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
    public void dtoClassTest() {
        assertNotNull(target.dtoClass());
        assertEquals(PersonTO.class, target.dtoClass());
    }

    @Test
    public void entityClassTest() {
        assertNotNull(target.entityClass());
        assertEquals(Person.class, target.entityClass());
    }

    @Test
    public void getRepository() {
        assertEquals(repository, target.getRepository());
    }

}
