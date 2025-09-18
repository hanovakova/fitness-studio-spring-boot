package com.fitnessstudiospringboot.service;

import com.fitnessstudiospringboot.model.FitnessClass;
import com.fitnessstudiospringboot.repository.FitnessClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FitnessClassService {

    private final FitnessClassRepository repo;

    public FitnessClassService(FitnessClassRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void add(FitnessClass cl) {
        repo.save(cl);
    }

    @Transactional(readOnly = true)
    public List<FitnessClass> getClasses() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public FitnessClass getClassById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Integer getClassCapacityById(Integer id) {
        FitnessClass cl = repo.findById(id).orElse(null);
        assert cl != null;
        return cl.getCapacity();
    }
}
