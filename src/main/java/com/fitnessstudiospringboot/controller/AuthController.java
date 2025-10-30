/*
package com.fitnessstudiospringboot.controller;

import com.fitnessstudiospringboot.controller.bean.RegistrationBean;
import com.fitnessstudiospringboot.model.User;
import com.fitnessstudiospringboot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "loginView";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        User user = userService.validateUser(username, password);
        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("loggedIn", true);
            return "redirect:/confirmation";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "loginView";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/fitnessClasses";
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "registrationConfirmedView";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("bean", new RegistrationBean());
        model.addAttribute("errors", new HashMap<String, String>());
        return "registerView";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute RegistrationBean bean,
            HttpSession session,
            Model model) throws IOException {

        Map<String, String> errors = validate(bean);

        if (!errors.isEmpty()) {
            bean.setPassword1("");
            bean.setPassword2("");
            model.addAttribute("bean", bean);
            model.addAttribute("errors", errors);
            return "registerView";
        }

        MultipartFile avatar = bean.getAvatarFile();
        // Handle avatar upload
        if (!avatar.isEmpty()) {
            String ext = Objects.requireNonNull(avatar.getOriginalFilename())
                    .substring(avatar.getOriginalFilename().lastIndexOf("."));
            String avatarName = UUID.randomUUID() + ext;
            avatar.transferTo(new File("uploads/" + avatarName)); // store locally
            bean.setAvatar(avatarName); // store path in bean
        }

        Integer userId = userService.add(transformToDomain(bean));

        session.setAttribute("userId", userId);
        session.setAttribute("username", bean.getUsername());
        session.setAttribute("name", bean.getName());
        session.setAttribute("loggedIn", true);
        session.setAttribute("avatar", bean.getAvatar());

        return "redirect:/confirmation";
    }

    private Map<String, String> validate(RegistrationBean bean) {
        Map<String, String> errors = new HashMap<>();

        // Username
        if (bean.getUsername() == null || bean.getUsername().isBlank()) {
            errors.put("username", "Username cannot be empty");
        } else if (bean.getUsername().length() < 2) {
            errors.put("username", "Username is too short (min 2 characters)");
        } else if (bean.getUsername().length() > 16) {
            errors.put("username", "Username is too long (max 16 characters)");
        } else if (!bean.getUsername().matches("\\w+")) {
            errors.put("username", "Username can only contain letters, numbers, and underscores");
        }

        // Password
        if (bean.getPassword1() == null || bean.getPassword1().isBlank()) {
            errors.put("password1", "Password cannot be empty");
        } else if (bean.getPassword1().length() < 4) {
            errors.put("password1", "Password is too short (min 4 characters)");
        } else if (bean.getPassword1().length() > 32) {
            errors.put("password1", "Password is too long (max 32 characters)");
        }

        // Repeat password
        if (bean.getPassword2() == null || bean.getPassword2().isBlank()) {
            errors.put("password2", "Please repeat your password");
        } else if (!bean.getPassword2().equals(bean.getPassword1())) {
            errors.put("password2", "Passwords do not match");
        }

        // Name
        if (bean.getName() == null || bean.getName().isBlank()) {
            errors.put("name", "Full name cannot be empty");
        } else if (bean.getName().length() < 2) {
            errors.put("name", "Full name is too short (min 2 characters)");
        } else if (bean.getName().length() > 32) {
            errors.put("name", "Full name is too long (max 32 characters)");
        }

        // Email
        if (bean.getEmail() == null || bean.getEmail().isBlank()) {
            errors.put("email", "Email cannot be empty");
        } else if (!bean.getEmail().matches("\\w+@(\\w+\\.)+\\w+")) {
            errors.put("email", "Email format is invalid");
        }

        return errors;
    }

    private User transformToDomain(RegistrationBean bean) {
        User user = new User();
        user.setUsername(bean.getUsername());
        user.setPassword(bean.getPassword1());
        user.setEmail(bean.getEmail());
        user.setName(bean.getName());
        user.setAdvertisement(bean.isAdvertisement());
        user.setAvatarPath(bean.getAvatar());
        return user;
    }
}
*/
