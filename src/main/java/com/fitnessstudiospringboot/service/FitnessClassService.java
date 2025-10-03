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
}
