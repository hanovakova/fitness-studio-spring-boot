package com.fitnessstudiospringboot.repository;

import com.fitnessstudiospringboot.model.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Integer> {

    @Query(value = "SELECT * FROM fitness_classes f " +
            "WHERE EXTRACT(HOUR FROM f.start_time) >= :startHour " +
            "AND EXTRACT(HOUR FROM f.end_time) <= :endHour",
            nativeQuery = true)
    List<FitnessClass> findClassesByHourRange(
            @Param("startHour") int startHour,
            @Param("endHour") int endHour
    );
}
