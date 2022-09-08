package com.rodrigo.foodapi.domain.exception;

public class FormPaymentNotFoundException extends EntityNotFoundException{

    public static final String MSG_FORM_PAYMENT_NOT_FOUND = "Não existe um cadastro de forma de pagamento com código %d ";

    public FormPaymentNotFoundException(String message) {
        super(message);
    }

    public FormPaymentNotFoundException(Long stateId) {
        this(String.format(MSG_FORM_PAYMENT_NOT_FOUND, stateId));
    }
}
