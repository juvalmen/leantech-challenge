package com.leantech.challenge.service.mapper;

import com.leantech.challenge.pojo.api.EmployeeTO;
import com.leantech.challenge.pojo.api.PersonTO;
import com.leantech.challenge.pojo.api.PositionTO;
import com.leantech.challenge.pojo.entity.Employee;
import com.leantech.challenge.pojo.entity.Person;
import com.leantech.challenge.pojo.entity.Position;
import com.leantech.challenge.utils.CollectionUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Mapper {

    private MapperFacade mapperFacade;

    public <I, O> O map(final I input, final Class<O> clazz) {
        return mapperFacade.map(input, clazz);
    }

    public <I, O> List<O> map(final List<I> input, final Class<O> clazz) {
        return CollectionUtils.safeStream(input).map(it -> this.map(it, clazz)).collect(toList());
    }

    @PostConstruct
    public void init() {
        final DefaultMapperFactory factory = new DefaultMapperFactory.Builder().mapNulls(false).build();

        factory.classMap(PersonTO.class, Person.class).byDefault().register();
        factory.classMap(PositionTO.class, Position.class).byDefault().register();
        factory.classMap(EmployeeTO.class, Employee.class)
                .field("positionTO", "position")
                .field("personTO", "person")
                .byDefault().register();

        this.mapperFacade = factory.getMapperFacade();
    }


}