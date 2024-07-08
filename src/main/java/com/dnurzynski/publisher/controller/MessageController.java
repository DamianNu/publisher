package com.dnurzynski.publisher.controller;

import com.dnurzynski.publisher.model.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
