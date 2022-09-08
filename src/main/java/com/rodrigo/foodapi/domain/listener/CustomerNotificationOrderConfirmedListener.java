package com.rodrigo.foodapi.domain.listener;

import com.rodrigo.foodapi.domain.event.ConfirmedOrderEvent;
import com.rodrigo.foodapi.domain.model.Demand;
import com.rodrigo.foodapi.domain.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CustomerNotificationOrderConfirmedListener {

    @Autowired
    private SendEmailService sendEmailService;

    @TransactionalEventListener
    public void whenConfirmingOrder(ConfirmedOrderEvent event){
        Demand demand = event.getDemand();

        var message = SendEmailService.Message.builder()
                .subject(demand.getRestaurant().getName() + " - Pedido confirmado")
                .body("pedido-confirmado.html")
                .variable("demand", demand)
                .recipient(demand.getClient().getEmail())
                .build();
        sendEmailService.send(message);
    }
}
