package com.rodrigo.foodapi.api.v1.assembler.formpayment;

import com.rodrigo.foodapi.api.v1.model.request.FormPaymentRequest;
import com.rodrigo.foodapi.domain.model.FormPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormPaymentRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormPayment toDomainObject(FormPaymentRequest formPaymentRequest) {
        return modelMapper.map(formPaymentRequest, FormPayment.class);
    }

    public void copyToDomainObject(FormPaymentRequest formPaymentRequest, FormPayment formPayment) {
        modelMapper.map(formPaymentRequest, formPayment);
    }
}
