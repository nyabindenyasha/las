package com.anesuandtatenda.studentlecturerplatform.service;

import com.anesuandtatenda.studentlecturerplatform.local.exceptions.InvalidRequestException;
import com.anesuandtatenda.studentlecturerplatform.repo.BaseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.NoSuchElementException;


public abstract class BaseServiceImpl<T, C, U> implements BaseService<T, C, U> {

    protected final BaseRepository<T> repository;

    public BaseServiceImpl(BaseRepository<T> repository) {
        this.repository = repository;
    }

    public T findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> {
            return new NoSuchElementException(this.getEntityClass().getSimpleName() + " record not found.");
        });
    }

    @Transactional
    public void delete(Long id) {
        try {
            this.repository.deleteById(id);
        } catch (DataIntegrityViolationException var3) {
            throw new InvalidRequestException("You can not delete this record as it might be used by another record");
        }
    }

    public Page<T> findAll(Pageable pageable, String searchQuery) {
            return this.repository.findAll(pageable);
    }

    public Collection<T> findAll() {
        return this.repository.findAll();
    }

    protected abstract Class<T> getEntityClass();

}

