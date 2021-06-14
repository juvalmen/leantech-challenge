package com.leantech.challenge.persistence.store;

import com.leantech.challenge.persistence.repository.PositionRepository;
import com.leantech.challenge.pojo.api.PositionTO;
import com.leantech.challenge.pojo.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PositionStore extends Store<Position, PositionTO> {

    @Autowired
    private PositionRepository repository;

    @Override
    protected PositionRepository getRepository() {
        return repository;
    }

    @Override
    protected Class<Position> entityClass() {
        return Position.class;
    }

    @Override
    protected Class<PositionTO> dtoClass() {
        return PositionTO.class;
    }

}
