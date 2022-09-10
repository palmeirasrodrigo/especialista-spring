package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.assembler.formpayment.FormPaymentModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.formpayment.FormPaymentRequestDisassembler;
import com.rodrigo.foodapi.api.v1.model.request.FormPaymentRequest;
import com.rodrigo.foodapi.api.v1.model.response.FormPaymentResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.FormPaymentControllerOpenApi;
import com.rodrigo.foodapi.domain.model.FormPayment;
import com.rodrigo.foodapi.domain.repository.FormPaymentRepository;
import com.rodrigo.foodapi.domain.service.FormPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/v1/form-payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormPaymentController implements FormPaymentControllerOpenApi {

    @Autowired
    private FormPaymentService formPaymentService;

    @Autowired
    private FormPaymentModelAssembler formPaymentModelAssembler;

    @Autowired
    private FormPaymentRepository formPaymentRepository;

    @Autowired
    private FormPaymentRequestDisassembler formPaymentRequestDisassembler;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<FormPaymentResponse>> listAll(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = formPaymentRepository.getLastUpdateDate();

        if(lastUpdateDate != null) {
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        //já temos condições de saber se continua ou não o processamento
        if(request.checkNotModified(eTag)){
            return null;
        }

        CollectionModel<FormPaymentResponse> formPaymentResponses = formPaymentModelAssembler.toCollectionModel(formPaymentService.listAll());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())//usa cacheado
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())//só usa localmente
               // .cacheControl(CacheControl.noCache())// sempre vai ter uma validação
                //.cacheControl(CacheControl.noStore())//desativa cache
                .eTag(eTag)
                .body(formPaymentResponses);
    }

    @Override
    @GetMapping("/{formPaymentId}")
    public ResponseEntity<FormPaymentResponse> find(@PathVariable Long formPaymentId, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime lastUpdateDate = formPaymentRepository.getLastUpdateDate();

        if(lastUpdateDate != null) {
            eTag = String.valueOf(lastUpdateDate.toEpochSecond());
        }

        //já temos condições de saber se continua ou não o processamento
        if(request.checkNotModified(eTag)){
            return null;
        }

        FormPaymentResponse formPaymentResponse = formPaymentModelAssembler.toModel(formPaymentService.find(formPaymentId));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formPaymentResponse);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormPaymentResponse create(@RequestBody FormPaymentRequest formPaymentRequest) {
        FormPayment formPayment = formPaymentRequestDisassembler.toDomainObject(formPaymentRequest);

        return formPaymentModelAssembler.toModel(formPaymentService.create(formPayment));
    }

    @Override
    @PutMapping("/{formPaymentId}")
    public FormPaymentResponse update(@PathVariable Long formPaymentId, @RequestBody FormPaymentRequest formPaymentRequest) {
        FormPayment actualFormPayment = formPaymentService.find(formPaymentId);
        formPaymentRequestDisassembler.copyToDomainObject(formPaymentRequest, actualFormPayment);
        return formPaymentModelAssembler.toModel(formPaymentService.create(actualFormPayment));
    }

    @Override
    @DeleteMapping("/formPaymentId}")
    public void remove(@PathVariable Long formPaymentId) {
        formPaymentService.remove(formPaymentId);
    }
}