package com.fitnessstudiospringboot.service;

import com.fitnessstudiospringboot.model.FitnessClass;
import com.fitnessstudiospringboot.repository.FitnessClassRepository;
import com.fitnessstudiospringboot.util.BeanCopyUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
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
    public Integer add(FitnessClass cl) {
        return repo.save(cl).getId();
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
    public List<FitnessClass> getClassesByTimeFrame(String timeFrame, List<FitnessClass> classes) {
        int startHour, endHour;

        endHour = switch (timeFrame.toLowerCase()) {
            case "morning" -> { startHour = 6; yield 12; }
            case "afternoon" -> { startHour = 12; yield 18; }
            case "evening" -> { startHour = 18; yield 23; }
            default -> { startHour = 0; yield 23; }
        };

        return classes.stream()
                .filter(c -> {
                    int hour = c.getStartTime().getHours();
                    return hour >= startHour && hour < endHour;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FitnessClass> getClassesByClassType(String classType, List<FitnessClass> classes) {
        return classes.stream().filter(c -> classType.equalsIgnoreCase(c.getClassType()))
                .toList();
    }

    @Transactional
    public boolean isClassCapacityExceeded(Integer id, Integer numberOfSignedUpClasses) {

        FitnessClass fitnessClass = repo.findByIdWithLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Fitness class not found with ID: " + id));

        int classCapacity = fitnessClass.getCapacity();

        return numberOfSignedUpClasses >= classCapacity;
    }

    @Transactional
    public FitnessClass updateClass(int id, FitnessClass fitnessClassDetails) {
        FitnessClass fitnessClass = repo.findByIdWithLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Fitness class not found with ID: " + id));

        BeanUtils.copyProperties(fitnessClassDetails, fitnessClass, "id");
        return repo.save(fitnessClass);
    }

    @Transactional
    public FitnessClass partialUpdateClass(int id, FitnessClass fitnessClassDetails) {
        FitnessClass fitnessClass = repo.findByIdWithLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Fitness class not found with ID: " + id));

        BeanCopyUtils.copyNonNullProperties(fitnessClassDetails, fitnessClass);
        return fitnessClass;
    }

    @Transactional
    public void deleteClass(int id) {
        repo.deleteById(id);
    }
}
