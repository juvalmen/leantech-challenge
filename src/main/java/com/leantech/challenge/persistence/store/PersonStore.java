package com.leantech.challenge.persistence.store;

import com.leantech.challenge.persistence.repository.PersonRepository;
import com.leantech.challenge.pojo.api.PersonTO;
import com.leantech.challenge.pojo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonStore extends Store<Person, PersonTO> {

    @Autowired
    private PersonRepository repository;

    @Override
    protected PersonRepository getRepository() {
        return repository;
    }

    @Override
    protected Class<Person> entityClass() {
        return Person.class;
    }

    @Override
    protected Class<PersonTO> dtoClass() {
        return PersonTO.class;
    }

}
