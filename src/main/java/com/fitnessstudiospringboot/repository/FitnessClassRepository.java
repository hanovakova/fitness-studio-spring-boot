package com.fitnessstudiospringboot.repository;

import com.fitnessstudiospringboot.model.FitnessClass;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select fc from FitnessClass fc where fc.id = :id")
    Optional<FitnessClass> findByIdWithLock(@Param("id") Integer id);

}
