package divacademy.homemate.service;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.ObjectError;

public interface EmailService {
    @Async
    void sendTo(String email, Object subject);
}
