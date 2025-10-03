package com.fitnessstudiospringboot.repository;

import com.fitnessstudiospringboot.model.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Integer> { }
