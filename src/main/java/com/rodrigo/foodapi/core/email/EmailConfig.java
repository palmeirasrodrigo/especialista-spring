package com.rodrigo.foodapi.core.email;

import com.rodrigo.foodapi.domain.service.SendEmailService;
import com.rodrigo.foodapi.infrastructure.service.email.FakeSendEmailService;
import com.rodrigo.foodapi.infrastructure.service.email.SandboxSendEmailService;
import com.rodrigo.foodapi.infrastructure.service.email.SmtpSendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public SendEmailService sendEmailService() {
        return switch (emailProperties.getImpl()) {
            case FAKE -> new FakeSendEmailService();
            case SMTP -> new SmtpSendEmailService();
            case SANDBOX -> new SandboxSendEmailService();
            default -> null;
        };
    }
}
