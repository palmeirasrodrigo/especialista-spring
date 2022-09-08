package com.rodrigo.foodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    BUSINESS("erro-negocio","Violação de regra de negócio"),

    ENTITY_IN_USE("entidade-em-uso", "Entidade em uso"),
    RESOURCE_NOT_FOUND("recurso-nao-encontrado", "Recurso não encontrado"),

    SYSTEM_ERROR("erro-de-sistema", "Erro de sistema"),

    INCOMPREHENSIBLE_MESSAGE("mensagem-incompreensivel", "Mensagem incompreensível"),

    INVALID_DATA("dados-invalidos", "Dados inválidos"),

    INVALID_PARAMETER("parametro-invalido", "parâmetro inválido");


    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://food.com.br/" + path;
        this.title = title;
    }
}
