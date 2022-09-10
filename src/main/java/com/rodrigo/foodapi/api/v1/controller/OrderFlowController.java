package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.openapi.controller.OrderFlowControllerOpenApi;
import com.rodrigo.foodapi.domain.service.OrderFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/demands/{demandCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderFlowController implements OrderFlowControllerOpenApi {

    @Autowired
    private OrderFlowService orderFlowService;

    @Override
    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirm(@PathVariable String demandCode) {
        orderFlowService.confirm(demandCode);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancel(@PathVariable String demandCode) {
        orderFlowService.cancel(demandCode);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/delivered")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delivered(@PathVariable String demandCode) {
        orderFlowService.delivered(demandCode);
        return ResponseEntity.noContent().build();
    }

}
