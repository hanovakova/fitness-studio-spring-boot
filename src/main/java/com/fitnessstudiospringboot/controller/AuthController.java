package com.fitnessstudiospringboot.controller;

import com.fitnessstudiospringboot.controller.bean.RegistrationBean;
import org.springframework.ui.Model;
import com.fitnessstudiospringboot.model.User;
import com.fitnessstudiospringboot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            return "redirect:/confirmation";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "loginView";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/classes"; // redirect to classes page after logout
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "registrationConfirmedView";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("bean", new RegistrationBean());
        return "registerView";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute RegistrationBean regFormBean,
            @RequestParam("avatar") MultipartFile avatar,
            HttpSession session,
            Model model) throws IOException {

        Map<String, String> errors = validate(regFormBean);

        if (!errors.isEmpty()) {
            regFormBean.setPassword1("");
            regFormBean.setPassword2("");
            model.addAttribute("bean", regFormBean);
            model.addAttribute("errors", errors);
            return "registerView";
        }

        // Handle avatar upload
        if (!avatar.isEmpty()) {
            String ext = Objects.requireNonNull(avatar.getOriginalFilename())
                    .substring(avatar.getOriginalFilename().lastIndexOf("."));
            String avatarName = UUID.randomUUID() + ext;
            avatar.transferTo(new java.io.File("uploads/" + avatarName)); // store locally
            regFormBean.setAvatar(avatarName);
        }

        userService.add(transformToDomain(regFormBean));

        session.setAttribute("username", regFormBean.getUsername());
        session.setAttribute("name", regFormBean.getName());
        session.setAttribute("avatar", regFormBean.getAvatar());

        return "redirect:/confirmation";
    }

    private Map<String, String> validate(RegistrationBean bean) {
        Map<String, String> errors = new HashMap<>();
        validateString(bean.getUsername(), "\\w{2,16}", "username", errors);
        validateString(bean.getPassword1(), "\\w{4,32}", "password1", errors);
        validateString(bean.getName(), ".{2,32}", "name", errors);
        validateString(bean.getEmail(), "\\w+@(\\w+[.])+\\w+", "email", errors);
        if (bean.getPassword1() != null && !bean.getPassword1().equals(bean.getPassword2())) {
            errors.put("password2", "Passwords do not match");
        }
        return errors;
    }

    private void validateString(String str, String pattern, String key, Map<String, String> map) {
        if (str == null || !str.matches(pattern)) {
            map.put(key, "Invalid or empty value");
        }
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
