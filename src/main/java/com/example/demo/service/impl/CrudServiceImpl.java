package com.example.demo.service.impl;

import com.example.demo.entity.AbstractEntity;
import com.example.demo.service.CrudService;
import com.example.demo.utils.ConfigProperties;
import com.example.demo.utils.Utils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class CrudServiceImpl<T extends AbstractEntity, ID extends Serializable> implements CrudService<T, ID> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected ConfigProperties configProperties;
    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected Environment environment;

    protected ModelMapper modelMapper = new ModelMapper();

    protected JpaRepository<T, ID> repository;

    public CrudServiceImpl(JpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public T get(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public T create(T entity) {
        beforeCreate(entity);
        entity = repository.save(entity);
        return entity;
    }

    @Override
    public T update(ID id, T entity) {
        beforeUpdate(entity);
        T old = get(id);
        if (old == null) {
            throw new EntityNotFoundException("No entity with ID " + id);
        }
        entity = repository.save(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        T entity = get(id);
        delete(entity);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    protected void beforeCreate(T entity) {
        try {
            Timestamp timeNow = Utils.getTimeNow(configProperties.getFormatStandardDate());
            entity.setCreatedDate(timeNow);
            entity.setUpdatedDate(timeNow);
        } catch (Exception ex) {
            logger.error("beforeCreate", ex);
        }
    }

    protected void beforeUpdate(T entity) {
        try {
            Timestamp timeNow = Utils.getTimeNow(configProperties.getFormatStandardDate());
            entity.setUpdatedDate(timeNow);
        } catch (Exception ex) {
            logger.error("beforeCreate", ex);
        }
    }
}
