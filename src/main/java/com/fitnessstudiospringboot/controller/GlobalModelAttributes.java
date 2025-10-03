package com.fitnessstudiospringboot.controller;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

@ControllerAdvice
public class GlobalModelAttributes {
    private final MessageSource messageSource;

    public GlobalModelAttributes(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ModelAttribute("lang")
    public String lang(HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);
        return locale.getLanguage();
    }

    @ModelAttribute
    public void addGlobalMessages(Model model, HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);

        model.addAttribute("registrationTitle", messageSource.getMessage("registration.title", null, locale));
        model.addAttribute("registrationUsername", messageSource.getMessage("registration.username", null, locale));
        model.addAttribute("registrationPassword", messageSource.getMessage("registration.password", null, locale));
        model.addAttribute("registrationRepeatPassword", messageSource.getMessage("registration.repeat_password", null, locale));
        model.addAttribute("registrationFullName", messageSource.getMessage("registration.full_name", null, locale));
        model.addAttribute("registrationEmail", messageSource.getMessage("registration.email", null, locale));
        model.addAttribute("registrationAdvertisement", messageSource.getMessage("registration.advertisement", null, locale));
        model.addAttribute("registrationAvatar", messageSource.getMessage("registration.avatar", null, locale));
        model.addAttribute("registrationSubmit", messageSource.getMessage("registration.submit", null, locale));
        model.addAttribute("registrationQuestion", messageSource.getMessage("registration.question", null, locale));
        model.addAttribute("registrationLogin", messageSource.getMessage("registration.login", null, locale));
    }
}
