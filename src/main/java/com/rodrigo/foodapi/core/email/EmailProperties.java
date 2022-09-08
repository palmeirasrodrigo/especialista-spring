package com.rodrigo.foodapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Setter
@Getter
@Component
@ConfigurationProperties("food.email")
public class EmailProperties {

    private Implementation impl = Implementation.FAKE;

    @NotNull
    private String sender;

    private Sandbox sandbox = new Sandbox();

    public enum Implementation {
        SMTP, FAKE, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {

        private String recipient;

    }
}
