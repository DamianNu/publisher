package com.dnurzynski.publisher.service;

import com.dnurzynski.publisher.model.Notification;
import com.dnurzynski.publisher.model.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public NotificationServiceImpl(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendStudentNotification(Long studentId) {
        Student student = restTemplate.exchange("http://localhost:8085/students/"
                + studentId, HttpMethod.GET, HttpEntity.EMPTY, Student.class).getBody();
        Notification notification = new Notification();
        notification.setEmail(student.getEmail());
        notification.setTitle("Witaj! " + student.getFirstName());
        notification.setBody("Miło, że jesteś z nami "
                + student.getFirstName() + " " + student.getLastName() + "!");
        rabbitTemplate.convertAndSend("kurs", notification);
    }
}
