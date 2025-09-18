package com.fitnessstudiospringboot.service;

import com.fitnessstudiospringboot.model.FitnessClass;
import com.fitnessstudiospringboot.model.UserClass;
import com.fitnessstudiospringboot.model.UserClassKey;
import com.fitnessstudiospringboot.repository.FitnessClassRepository;
import com.fitnessstudiospringboot.repository.UserClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserClassService {
    UserClassRepository userClassRepo;
    FitnessClassRepository fitnessClassRepo;

    public UserClassService(UserClassRepository userClassRepository, FitnessClassRepository fitnessClassRepository) {
        this.userClassRepo = userClassRepository;
        this.fitnessClassRepo = fitnessClassRepository;
    }

    @Transactional(readOnly = true)
    public boolean isClassCapacityExceeded(Integer fitnessClassId) {
        int numberOfSignedUpClasses = userClassRepo.findAll().size();

        FitnessClass fitnessClass = fitnessClassRepo.findById(fitnessClassId).orElse(null);
        assert fitnessClass != null;
        int classCapacity = fitnessClass.getCapacity();

        return numberOfSignedUpClasses >= classCapacity;
    }

    @Transactional
    public void signUp(UserClass userClass) {
        UserClassKey userClassKey = userClass.getId();
        Integer fitnessClassId = userClassKey.getClassId();
        if (!isClassCapacityExceeded(fitnessClassId)) {
            userClassRepo.save(userClass);
        }
    }

    @Transactional(readOnly = true)
    public UserClass getClassById(UserClassKey id) {
        return userClassRepo.findById(id).orElse(null);
    }

    @Transactional
    public void dropClass(UserClassKey id) {
        userClassRepo.deleteById(id);
    }

    @Transactional
    public void setPaid(UserClassKey userClassKey, Boolean paid) {
        UserClass userClass = getClassById(userClassKey);
        userClass.setPaid(paid);
    }

    @Transactional(readOnly = true)
    public boolean isPaid(UserClassKey userClassKey) {
        return getClassById(userClassKey).isPaid();
    }

    public List<Integer> getEnrolledClassIds(int userId) {
        return userClassRepo.getUserEnrolledClassIds(userId);
    }
}
