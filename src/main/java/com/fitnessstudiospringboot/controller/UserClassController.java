/*
package com.fitnessstudiospringboot.controller;

import com.fitnessstudiospringboot.model.UserClassKey;
import com.fitnessstudiospringboot.service.FitnessClassService;
import com.fitnessstudiospringboot.service.UserClassService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user-classes")
public class UserClassController {

    private final FitnessClassService fitnessClassService;
    private final UserClassService userClassService;

    public UserClassController(FitnessClassService fitnessClassService,
                                  UserClassService userClassService) {
        this.fitnessClassService = fitnessClassService;
        this.userClassService = userClassService;
    }

    */
/*@GetMapping("/selected")
    public String viewSelectedClasses(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        FitnessClassController.setEnrolledClassesInSession(session, userId, userClassService, fitnessClassService);

        boolean isLoggedIn = session.getAttribute("loggedIn") != null && (boolean) session.getAttribute("loggedIn");
        model.addAttribute("loggedIn", isLoggedIn);
        return "selectedClassesView";
    }*//*


    @PostMapping("/drop")
    public String dropClass(@RequestParam("enrolledClassId") int enrolledClassId,
                            HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            UserClassKey key = new UserClassKey(userId, enrolledClassId);
            userClassService.dropClass(key);
        }
        return "redirect:/user-classes/selected";
    }

    @GetMapping("/purchased")
    public String viewPurchasedClasses() {
        return "purchasedClassesView";
    }
}*/
