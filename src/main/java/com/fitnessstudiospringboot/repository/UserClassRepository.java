package com.fitnessstudiospringboot.repository;

import com.fitnessstudiospringboot.model.UserClass;
import com.fitnessstudiospringboot.model.UserClassKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClassRepository extends JpaRepository<UserClass, UserClassKey> {

    @Query(value = "SELECT class_id FROM user_classes WHERE user_id = :userId", nativeQuery = true)
    List<Integer> getUserEnrolledClassIds(@Param("userId") int userId);
}