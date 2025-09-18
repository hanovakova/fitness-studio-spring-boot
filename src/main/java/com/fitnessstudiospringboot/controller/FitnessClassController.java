package com.fitnessstudiospringboot.controller;

import com.fitnessstudiospringboot.model.FitnessClass;
import com.fitnessstudiospringboot.model.UserClass;
import com.fitnessstudiospringboot.model.UserClassKey;
import com.fitnessstudiospringboot.service.FitnessClassService;
import com.fitnessstudiospringboot.service.UserClassService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/classes")
public class FitnessClassController {

    private final FitnessClassService fitnessClassService;
    private final UserClassService userClassService;

    public FitnessClassController(FitnessClassService fitnessClassService,
                                  UserClassService userClassService) {
        this.fitnessClassService = fitnessClassService;
        this.userClassService = userClassService;
    }

    @GetMapping
    public String showClasses(Model model, HttpSession session) {
        List<FitnessClass> classes = fitnessClassService.getClasses();
        model.addAttribute("fitnessClasses", classes);

        boolean loggedIn = false;
        Map<Integer, Boolean> classIdsPaid = new HashMap<>();
        Map<Integer, Boolean> classIdsCapacityExceeded = new HashMap<>();

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            loggedIn = true;

            List<Integer> enrolledClassIds = userClassService.getEnrolledClassIds(userId);
            for (Integer classId : enrolledClassIds) {
                UserClassKey key = new UserClassKey(classId, userId);
                classIdsPaid.put(classId, userClassService.isPaid(key));
            }
        }

        for (FitnessClass fitnessClass : classes) {
            classIdsCapacityExceeded.put(fitnessClass.getId(),
                    userClassService.isClassCapacityExceeded(fitnessClass.getId()));
        }

        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("classIdsPaid", classIdsPaid);
        model.addAttribute("classIdsCapacityExceeded", classIdsCapacityExceeded);

        return "classesView";
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> signUpForClass(@RequestParam("classId") int classId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.put("status", "unauthorized");
            return response;
        }

        try {
            UserClassKey key = new UserClassKey(classId, userId);
            UserClass userClass = new UserClass(key, false);
            userClassService.signUp(userClass);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}