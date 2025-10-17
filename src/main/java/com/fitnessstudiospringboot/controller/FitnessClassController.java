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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fitnessClasses")
public class FitnessClassController {

    private final FitnessClassService fitnessClassService;
    private final UserClassService userClassService;

    public FitnessClassController(FitnessClassService fitnessClassService,
                                  UserClassService userClassService) {
        this.fitnessClassService = fitnessClassService;
        this.userClassService = userClassService;
    }

    @GetMapping
    public String showClasses(
            @RequestParam(required = false) String classTime,
            @RequestParam(required = false) String classType,
            Model model, HttpSession session) {

        List<FitnessClass> classes = fitnessClassService.getClasses();
        if (classTime != null && !classTime.isBlank()) {
            classes = fitnessClassService.getClassesByTimeFrame(classTime.toLowerCase(), classes);
        }

        if (classType != null && !classType.isBlank()) {
            classes = fitnessClassService.getClassesByClassType(classType.toLowerCase(), classes);
        }

        Map<String, Boolean> classIdsPaid = new HashMap<>();
        Map<String, Boolean> classIdsCapacityExceeded = new HashMap<>();

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {

            List<Integer> enrolledClassIds = userClassService.getEnrolledClassIds(userId);
            for (Integer classId : enrolledClassIds) {
                UserClassKey key = new UserClassKey(userId, classId);
                classIdsPaid.put(String.valueOf(classId), userClassService.isPaid(key));
            }
        }

        for (FitnessClass fitnessClass : classes) {
            int classId = fitnessClass.getId();
            int numberOfSignedUpClasses = userClassService.getNumberOfSignedUpClasses(classId);
            boolean isClassCapacityExceeded = fitnessClassService.isClassCapacityExceeded(classId, numberOfSignedUpClasses);

            classIdsCapacityExceeded.put(String.valueOf(classId),isClassCapacityExceeded);
        }

        boolean isLoggedIn = session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn");
        model.addAttribute("loggedIn", isLoggedIn);

        setEnrolledClassesInSession(session, userId, userClassService, fitnessClassService);

        model.addAttribute("timeOptions", List.of("Morning", "Afternoon", "Evening"));
        model.addAttribute("typeOptions", Map.of(
                "FitnessClass", "Fitness Class",
                "YogaClass", "Yoga",
                "SpinningClass", "Spinning"
        ));
        model.addAttribute("fitnessClasses", classes);
        model.addAttribute("classTime", classTime);
        model.addAttribute("classType", classType);
        model.addAttribute("classIdsPaid", classIdsPaid);
        model.addAttribute("classIdsCapacityExceeded", classIdsCapacityExceeded);

        return "fitnessClassesView";
    }

    static void setEnrolledClassesInSession(HttpSession session, Integer userId, UserClassService userClassService, FitnessClassService fitnessClassService) {
        List<Integer> enrolledClassIds = new ArrayList<>();
        if (userId != null) {
            enrolledClassIds = userClassService.getEnrolledClassIds(userId);
        }

        List<FitnessClass> enrolledClasses = new ArrayList<>();
        if (!enrolledClassIds.isEmpty()) {
            for (Integer classId : enrolledClassIds) {
                UserClassKey key = new UserClassKey(userId, classId);
                if (!userClassService.isPaid(key)) {
                    enrolledClasses.add(fitnessClassService.getClassById(classId));
                }
            }
        }
        session.setAttribute("enrolledClassIds", enrolledClassIds);
        session.setAttribute("enrolledClasses", enrolledClasses);
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
            int numberOfSignedUpClasses = userClassService.getNumberOfSignedUpClasses(classId);
            boolean isClassCapacityExceeded = fitnessClassService.isClassCapacityExceeded(classId, numberOfSignedUpClasses);

            UserClassKey key = new UserClassKey(userId, classId);
            UserClass userClass = new UserClass(key, false);
            userClassService.signUp(userClass, isClassCapacityExceeded);
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}