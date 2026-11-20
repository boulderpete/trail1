package com.example.timelogger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RestController
public class TimeLoggerApplication {

    @Autowired
    private JavaMailSender mailSender;

    public static void main(String[] args) {
        SpringApplication.run(TimeLoggerApplication.class, args);
    }

    @GetMapping("/log")
    public String logTime(@RequestParam String email) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@timelogger.com");
            message.setTo(email);
            message.setSubject("Deine geloggte Uhrzeit");
            message.setText("Die aktuelle Uhrzeit wurde erfolgreich erfasst:\n\n" + currentTime);
            
            mailSender.send(message);
            return "Uhrzeit (" + currentTime + ") erfolgreich geloggt und an " + email + " gesendet.";
        } catch (Exception e) {
            return "Fehler beim Senden der E-Mail: " + e.getMessage();
        }
    }
}
