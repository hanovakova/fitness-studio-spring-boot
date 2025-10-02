package com.fitnessstudiospringboot.controller;

import com.fitnessstudiospringboot.model.FitnessClass;
import com.fitnessstudiospringboot.model.UserClassKey;
import com.fitnessstudiospringboot.service.FitnessClassService;
import com.fitnessstudiospringboot.service.UserClassService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckOutController {

    private final FitnessClassService fitnessClassService;
    private final UserClassService userClassService;

    public CheckOutController(FitnessClassService fitnessClassService,
                              UserClassService userClassService) {
        this.fitnessClassService = fitnessClassService;
        this.userClassService = userClassService;
    }

    @GetMapping("/checkout")
    public String showCheckoutPage() {
        return "checkoutView";
    }

    @PostMapping("/checkout")
    public String processCheckout(HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        List<Integer> enrolledClassIds = new ArrayList<>();

        if (userId != null) {
            enrolledClassIds = userClassService.getEnrolledClassIds(userId);
        }

        // Mark classes as paid
        for (Integer classId : enrolledClassIds) {
            UserClassKey userClassKey = new UserClassKey(userId, classId);
            userClassService.setPaid(userClassKey, true);
        }

        // Collect purchased classes
        List<FitnessClass> purchasedClasses = new ArrayList<>();
        for (Integer classId : enrolledClassIds) {
            purchasedClasses.add(fitnessClassService.getClassById(classId));
        }

        session.setAttribute("purchasedClasses", purchasedClasses);

        // Redirect to purchased classes page
        return "redirect:/purchasedClasses";
    }
}
