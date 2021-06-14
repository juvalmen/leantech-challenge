package com.leantech.challenge.service.person;

import com.leantech.challenge.persistence.store.PersonStore;
import com.leantech.challenge.pojo.api.PersonTO;
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
public class PersonServiceTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    private PersonService target;

    @Mock
    private PersonStore personStore;

    @BeforeClass
    public void beforeClass() {
        openMocks(this);
    }

    @Test
    public void saveTest() {
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        personTO.setId(null);
        final PersonTO expected = EASY_RANDOM.nextObject(PersonTO.class);
        when(personStore.save(personTO)).thenReturn(expected);
        final PersonTO actual = target.save(personTO);
        assertEquals(expected, actual);
        verify(personStore, times(2)).save(any(PersonTO.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void saveIllegalArgumentExceptionTest() {
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        when(personStore.save(personTO)).thenThrow(IllegalArgumentException.class);
        final PersonTO actual = target.save(personTO);
        verify(personStore, times(1)).save(any(PersonTO.class));
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void findByIdEntityNotFoundExceptionTest() {
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        final PersonTO expected = EASY_RANDOM.nextObject(PersonTO.class);
        when(personStore.findById(personTO.getId())).thenThrow(EntityNotFoundException.class);
        final PersonTO actual = target.findById(personTO);
        verify(personStore, times(2)).findById(any(Long.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByIdIllegalArgumentExceptionTest() {
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        personTO.setId(null);
        when(personStore.findById(null)).thenThrow(IllegalArgumentException.class);
        final PersonTO actual = target.findById(personTO);
        verify(personStore, times(2)).findById(null);
    }

    @Test
    public void findByIdTest() {
        final PersonTO personTO = EASY_RANDOM.nextObject(PersonTO.class);
        final PersonTO expected = EASY_RANDOM.nextObject(PersonTO.class);
        when(personStore.findById(personTO.getId())).thenReturn(expected);
        final PersonTO actual = target.findById(personTO);
        assertEquals(expected, actual);
        verify(personStore, times(2)).findById(any(Long.class));
    }

    @Test
    public void getAllTest() {
        final List<PersonTO> expected = newArrayList(EASY_RANDOM.nextObject(PersonTO.class));
        when(personStore.findAll()).thenReturn(expected);
        final List<PersonTO> actual = target.findAll();
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(personStore, times(1)).findAll();
    }

}
