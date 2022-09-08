package com.rodrigo.foodapi.domain.listener;

import com.rodrigo.foodapi.domain.event.CanceledOrderEvent;
import com.rodrigo.foodapi.domain.event.ConfirmedOrderEvent;
import com.rodrigo.foodapi.domain.model.Demand;
import com.rodrigo.foodapi.domain.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CustomerNotificationOrderCanceledListener {

    @Autowired
    private SendEmailService sendEmailService;

    @TransactionalEventListener
    public void whenCancelOrder(CanceledOrderEvent event){
        Demand demand = event.getDemand();

        var message = SendEmailService.Message.builder()
                .subject(demand.getRestaurant().getName() + " - Pedido cancelado")
                .body("pedido-cancelado.html")
                .variable("demand", demand)
                .recipient(demand.getClient().getEmail())
                .build();
        sendEmailService.send(message);
    }
}
