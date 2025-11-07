package com.fitnessstudiospringboot.controller;

import com.fitnessstudiospringboot.config.MessagingConfig;
import com.fitnessstudiospringboot.dto.PurchaseEvent;
import com.fitnessstudiospringboot.model.FitnessClass;
import com.fitnessstudiospringboot.model.UserClassKey;
import com.fitnessstudiospringboot.service.FitnessClassService;
import com.fitnessstudiospringboot.service.UserClassService;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CheckOutController {

    private final FitnessClassService fitnessClassService;
    private final UserClassService userClassService;
    private final RabbitTemplate rabbitTemplate;
    private static Logger logger = LogManager.getLogger(CheckOutController.class);

    public CheckOutController(FitnessClassService fitnessClassService,
                              UserClassService userClassService, RabbitTemplate rabbitTemplate) {
        this.fitnessClassService = fitnessClassService;
        this.userClassService = userClassService;
        this.rabbitTemplate = rabbitTemplate;
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

        List<FitnessClass> purchasedClasses = new ArrayList<>();
        float purchaseSum = 0f;

        // Mark classes as paid
        for (Integer classId : enrolledClassIds) {
            UserClassKey userClassKey = new UserClassKey(userId, classId);
            userClassService.setPaid(userClassKey, true);

            FitnessClass purchasedClass = fitnessClassService.getClassById(classId);
            purchasedClasses.add(purchasedClass);

            if (purchasedClass.getPrice() != null) {
                purchaseSum += purchasedClass.getPrice();
            }
        }

        UUID uuid = UUID.randomUUID();
        PurchaseEvent event = new PurchaseEvent(
                uuid,
                userId,
                purchasedClasses,
                purchaseSum,
                new Timestamp(System.currentTimeMillis())
        );

        try {
            rabbitTemplate.convertAndSend(MessagingConfig.PURCHASE_QUEUE, event);
            logger.info("Sent purchase event for user {} ({} classes, total ${})",
                    userId, purchasedClasses.size(), purchaseSum);
        } catch (Exception e) {
            logger.error("Failed to send purchase message to queue: {}", e.getMessage());
        }
        session.setAttribute("purchasedClasses", purchasedClasses);

        // Redirect to purchased classes page
        return "redirect:/user-classes/purchased";
    }
}
