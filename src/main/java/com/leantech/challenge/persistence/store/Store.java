package com.leantech.challenge.persistence.store;

import com.leantech.challenge.pojo.api.BaseEntityTO;
import com.leantech.challenge.pojo.entity.BaseEntity;
import com.leantech.challenge.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

/**
 * Layer used to abstract the Spring Data framework to the domain.
 */
@Transactional
public abstract class Store<E extends BaseEntity, D extends BaseEntityTO> {

    private static final String DTO_MESSAGE_MANDATORY = "Param 'dto' is mandatory";

    @Autowired
    protected Mapper mapper;

    protected abstract JpaRepository<E, Long> getRepository();

    protected abstract Class<E> entityClass();

    protected abstract Class<D> dtoClass();

    public D save(final D dto) {
        checkArgument(nonNull(dto), DTO_MESSAGE_MANDATORY);
        checkArgument(Objects.isNull(dto.getId()), "Param 'dto' can not have an id before save first time");
        return mapper.map(getRepository().save(mapper.map(dto, entityClass())), dtoClass());
    }

    public D update(final D dto) {
        checkArgument(nonNull(dto), DTO_MESSAGE_MANDATORY);
        checkArgument(nonNull(dto.getId()), "Param 'dto' needs to have an id to update it");
        return mapper.map(getRepository().save(mapper.map(dto, entityClass())), dtoClass());
    }

    public void delete(final D dto) {
        checkArgument(nonNull(dto), DTO_MESSAGE_MANDATORY);
        checkArgument(nonNull(dto.getId()), "Param 'dto' needs to have an id to update it");
        getRepository().delete(mapper.map(dto, entityClass()));
    }

    public D findById(final Long id) {
        checkArgument(nonNull(id), "Param 'id' is mandatory");
        return getRepository().findById(id)
                .map(it -> mapper.map(it, dtoClass()))
                .orElseThrow(() -> new EntityNotFoundException(format("Entity with id %s not found", id)));
    }

    public List<D> findAll() {
        return getRepository().findAll()
                .stream()
                .map(it -> mapper.map(it, dtoClass()))
                .collect(Collectors.toList());
    }

}
