package com.leantech.challenge.service.person;

import com.leantech.challenge.persistence.store.PersonStore;
import com.leantech.challenge.pojo.api.PersonTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.leantech.challenge.utils.ValidateUtils.checkArgument;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class PersonService {

    @Autowired
    private PersonStore personStore;

    public PersonTO save(final PersonTO to) {
        final PersonTO personTO = personStore.save(to);
        log.info("Person created successfully");
        return personTO;
    }

    public PersonTO findById(final PersonTO to) {
        checkArgument(nonNull(to), "PersonTO");
        final PersonTO personTO = personStore.findById(to.getId());
        log.info("Person found successfully");
        return personTO;
    }

    public List<PersonTO> findAll() {
        final List<PersonTO> personTOS = personStore.findAll();
        log.info("Persons consulted successfully");
        return personTOS;
    }
}
