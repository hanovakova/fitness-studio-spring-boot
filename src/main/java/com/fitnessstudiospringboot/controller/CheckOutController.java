package com.fitnessstudiospringboot.controller;

import com.fitnessstudiospringboot.config.MessagingConfig;
import com.fitnessstudiospringboot.dto.FitnessClassDTO;
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

        if (userId == null) {
            logger.warn("No user ID found in session during checkout.");
            return "redirect:/login";
        }

        // 1️⃣ Only unpaid classes = newly added ones
        List<Integer> unpaidClassIds = userClassService.getUnpaidClassIds(userId);

        if (unpaidClassIds.isEmpty()) {
            logger.info("No unpaid classes to purchase for user {}.", userId);
            return "redirect:/user-classes/purchased";
        }

        // 2️⃣ Collect those unpaid classes
        List<FitnessClass> unpaidClasses = new ArrayList<>();
        float purchaseSum = 0f;

        for (Integer classId : unpaidClassIds) {
            FitnessClass fitnessClass = fitnessClassService.getClassById(classId);
            if (fitnessClass != null) {
                unpaidClasses.add(fitnessClass);
                if (fitnessClass.getPrice() != null) {
                    purchaseSum += fitnessClass.getPrice();
                }
            }
        }

        List<FitnessClassDTO> unpaidClassesDTO = unpaidClasses.stream()
                .map(modelClass -> {
                    FitnessClassDTO dtoClass =
                            new FitnessClassDTO();

                    dtoClass.setId(modelClass.getId());
                    dtoClass.setName(modelClass.getName());
                    dtoClass.setDescription(modelClass.getDescription());
                    dtoClass.setStartTime(modelClass.getStartTime());
                    dtoClass.setEndTime(modelClass.getEndTime());
                    dtoClass.setInstructorName(modelClass.getInstructorName());
                    dtoClass.setPrice(modelClass.getPrice());
                    dtoClass.setCapacity(modelClass.getCapacity());
                    dtoClass.setImagePath(modelClass.getImagePath());

                    return dtoClass;
                })
                .toList();

        UUID uuid = UUID.randomUUID();
        PurchaseEvent event = new PurchaseEvent(
                uuid,
                userId,
                unpaidClassesDTO,
                purchaseSum,
                new Timestamp(System.currentTimeMillis())
        );

        try {
            rabbitTemplate.convertAndSend(MessagingConfig.PURCHASE_QUEUE, event);
            logger.info("Sent purchase event for user {} ({} classes, total ${})",
                    userId, unpaidClassesDTO.size(), purchaseSum);
        } catch (Exception e) {
            logger.error("Failed to send purchase message to queue: {}", e.getMessage());
        }

        session.setAttribute("pendingClasses", unpaidClasses);


        // Redirect to purchased classes page
        return "redirect:/user-classes/purchased";
    }
}
