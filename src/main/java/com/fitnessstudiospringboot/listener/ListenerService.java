package com.fitnessstudiospringboot.listener;

import com.fitnessstudiospringboot.config.MessagingConfig;
import com.fitnessstudiospringboot.dto.CancelledPurchase;
import com.fitnessstudiospringboot.dto.FitnessClassDTO;
import com.fitnessstudiospringboot.dto.PurchaseEvent;
import com.fitnessstudiospringboot.model.UserClassKey;
import com.fitnessstudiospringboot.service.UserClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ListenerService {

    private static final Logger logger = LoggerFactory.getLogger(ListenerService.class);
    private final UserClassService userClassService;

    public ListenerService(UserClassService userClassService) {
        this.userClassService = userClassService;
    }
    @RabbitListener(queues = MessagingConfig.CONFIRMATION_QUEUE)
    public void handleConfirmation(PurchaseEvent event) {
        try {
            logger.info("Received confirmation for purchase event: {}", event.getPurchaseId());

            Integer userId = event.getCustomerId();
            if (userId == null) {
                logger.error("Confirmation message has no userId!");
                return;
            }

            // Loop through the classes from the event and mark them as paid
            for (FitnessClassDTO dtoClass : event.getPurchasedClasses()) {
                UserClassKey key = new UserClassKey(userId, dtoClass.getId());
                userClassService.setPaid(key, true);
                logger.info("Marked class {} for user {} as PAID.", dtoClass.getId(), userId);
            }

        } catch (Exception e) {
            logger.error("Failed to process confirmation message: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = MessagingConfig.CANCELLED_QUEUE)
    public void handleCancellation(CancelledPurchase event) {
        try {
            logger.info("Received cancellation for purchase event: {}", event.getPurchaseId());

            Integer userId = event.getCustomerId();
            if (userId == null) {
                logger.error("Cancellation message has no userId!");
                return;
            }

            // Loop through the classes from the event and remove them from user_classes
            for (FitnessClassDTO dtoClass : event.getPurchasedClasses()) {
                UserClassKey key = new UserClassKey(userId, dtoClass.getId());
                userClassService.dropClass(key);
                logger.info("Removed class {} for user {} from the order.", dtoClass.getId(), userId);
            }

        } catch (Exception e) {
            logger.error("Failed to process cancellation message: {}", e.getMessage(), e);
        }
    }

}