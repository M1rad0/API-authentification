package mg.itu.auth.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;

@Service
public class MailService {

    private final MailSenderService mailSenderService;

    public MailService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    public void sendCodeValidationMail(String email, int code) throws MessagingException {
        Context context = new Context();
        context.setVariable("code", code);

        mailSenderService.sendMail(email, "Email Validation Code", "validation-mail", context);
    }

    public void sendCodeValidationLogin(String email, int code) throws MessagingException {
        Context context = new Context();
        context.setVariable("code", code);

        mailSenderService.sendMail(email, "Login Code", "login-mail", context);
    }
}