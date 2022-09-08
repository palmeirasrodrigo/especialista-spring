package com.rodrigo.foodapi.api.assembler.formpayment;

import com.rodrigo.foodapi.api.model.response.FormPaymentResponse;
import com.rodrigo.foodapi.domain.model.FormPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class FormPaymentModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormPaymentResponse toModel(FormPayment formPayment) {
        return modelMapper.map(formPayment, FormPaymentResponse.class);
    }

    public List<FormPaymentResponse> toCollectionModel(Collection<FormPayment> formPayments) {
        return formPayments.stream()
                .map(this::toModel)
                .toList();
    }
}
