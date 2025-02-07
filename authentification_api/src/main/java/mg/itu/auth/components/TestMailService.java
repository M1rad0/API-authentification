package mg.itu.auth.components;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mg.itu.auth.service.MailService;

import javax.mail.MessagingException;

@Component
public class TestMailService implements CommandLineRunner {

    private final MailService mailService;

    public TestMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void run(String... args) throws MessagingException {
        mailService.sendCodeValidationMail("kainrak312@gmail.com", 1234);
        System.out.println("Validation email sent!");

        mailService.sendCodeValidationLogin("kainrak312@gmail.com", 5678);
        System.out.println("Login email sent!");
    }
}