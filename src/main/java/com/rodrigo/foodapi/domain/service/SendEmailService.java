package com.rodrigo.foodapi.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface SendEmailService {

    void send(Message message);

    @Builder
    @Getter
    class Message {

        @Singular
        private Set<String>recipients;

        @NonNull
        private String subject;

        @NonNull
        private String body;

        @Singular
        private Map<String, Object> variables;
    }
}
