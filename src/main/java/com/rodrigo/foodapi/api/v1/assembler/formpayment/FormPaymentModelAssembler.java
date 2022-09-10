package com.rodrigo.foodapi.api.v1.assembler.formpayment;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.FormPaymentController;
import com.rodrigo.foodapi.api.v1.model.response.FormPaymentResponse;
import com.rodrigo.foodapi.domain.model.FormPayment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormPaymentModelAssembler extends RepresentationModelAssemblerSupport<FormPayment, FormPaymentResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public FormPaymentModelAssembler() {
        super(FormPaymentController.class, FormPaymentResponse.class);
    }

    @Override
    public FormPaymentResponse toModel(FormPayment formPayment) {
        FormPaymentResponse formPaymentResponse =
                createModelWithId(formPayment.getId(), formPayment);

        modelMapper.map(formPayment, formPaymentResponse);

        formPaymentResponse.add(algaLinks.linkToFormsPayment("formPayment"));

        return formPaymentResponse;
    }

    @Override
    public CollectionModel<FormPaymentResponse> toCollectionModel(Iterable<? extends FormPayment> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToFormsPayment());
    }
}
