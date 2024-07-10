package com.dnurzynski.publisher.controller;

import com.dnurzynski.publisher.model.Notification;
import com.dnurzynski.publisher.service.NotificationServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;
    private final NotificationServiceImpl notificationService;

    public MessageController(RabbitTemplate rabbitTemplate, NotificationServiceImpl notificationService) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String sentNotification(@RequestParam Long studentId) {
        notificationService.sendStudentNotification(studentId);
        return "Notyfikacja wysłana!";
    }

    @GetMapping("/message")
    public String sendMessage(@RequestParam String message) {
        rabbitTemplate.convertAndSend("kurs", message);
        return "Wrzucono widomość na RabbitMQ";
    }

    @PostMapping("/notification")
    public String sendNotification(@RequestBody Notification notification) {
        rabbitTemplate.convertAndSend("kurs", notification);
        return "Notyfikacja wysłana!";
    }
}
