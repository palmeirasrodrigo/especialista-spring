package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.openapi.controller.OrderFlowControllerOpenApi;
import com.rodrigo.foodapi.domain.service.OrderFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demands/{demandCode}")
public class OrderFlowController implements OrderFlowControllerOpenApi {

    @Autowired
    private OrderFlowService orderFlowService;

    @Override
    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable String demandCode) {
        orderFlowService.confirm(demandCode);
    }

    @Override
    @PutMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable String demandCode) {
        orderFlowService.cancel(demandCode);
    }

    @Override
    @PutMapping("/delivered")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivered(@PathVariable String demandCode) {
        orderFlowService.delivered(demandCode);
    }

}
