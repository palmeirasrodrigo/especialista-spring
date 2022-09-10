package com.rodrigo.foodapi.domain.model;

import com.rodrigo.foodapi.domain.event.CanceledOrderEvent;
import com.rodrigo.foodapi.domain.event.ConfirmedOrderEvent;
import com.rodrigo.foodapi.domain.exception.BusinessException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "demand")
public class Demand extends AbstractAggregateRoot<Demand> {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    @Embedded
    @Column(name = "delivery_address")
    private Address address;

    @Enumerated(EnumType.STRING)
    private StatusDemand status = StatusDemand.CRIADO;

    @CreationTimestamp
    @Column(name = "create_at", columnDefinition = "datetime")
    private OffsetDateTime createAt;

    @CreationTimestamp
    @Column(name = "confirmation_date", columnDefinition = "datetime")
    private OffsetDateTime confirmationDate;

    @CreationTimestamp
    @Column(name = "cancellation_date", columnDefinition = "datetime")
    private OffsetDateTime cancellationDate;

    @CreationTimestamp
    @Column(name = "delivery_date", columnDefinition = "datetime")
    private OffsetDateTime deliveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_payment_id", nullable = false)
    private FormPayment formPayments;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "person_client_id", nullable = false)
    private User client;

    @OneToMany(mappedBy = "demand", cascade = CascadeType.ALL)
    private List<ItemDemand> items = new ArrayList<>();

    public void calculateTotalValue() {
        getItems().forEach(ItemDemand::calculateTotalPrice);

        this.subtotal = getItems().stream()
                .map(item -> item.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.amount = this.subtotal.add(this.shippingFee);
    }

    public void confirm() {
        setStatus(StatusDemand.CONFIRMADO);
        setConfirmationDate(OffsetDateTime.now());

        registerEvent(new ConfirmedOrderEvent(this));
    }

    public void delivered() {
        setStatus(StatusDemand.ENTREGUE);
        setDeliveryDate(OffsetDateTime.now());
    }

    public void cancel() {
        setStatus(StatusDemand.CANCELADO);
        setCancellationDate(OffsetDateTime.now());

        registerEvent(new CanceledOrderEvent(this));
    }

    public boolean canBeConfirm() {
        return getStatus().canChangeTo(StatusDemand.CONFIRMADO);
    }

    public boolean canBeDelivered() {
        return getStatus().canChangeTo(StatusDemand.ENTREGUE);
    }

    public boolean canBeCancel() {
        return getStatus().canChangeTo(StatusDemand.CANCELADO);
    }


    private void setStatus(StatusDemand novoStatus) {
        if (getStatus().cannotChangeTo(novoStatus)) {
            throw new BusinessException(
                    String.format("Status do pedido %s não pode ser alterado de %s para %s",
                            getCode(), getStatus().getDescription(),
                            novoStatus.getDescription()));
        }

        this.status = novoStatus;
    }

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
