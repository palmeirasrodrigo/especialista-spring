package com.rodrigo.foodapi.domain.model;

import com.rodrigo.foodapi.core.validation.Groups;
import com.rodrigo.foodapi.core.validation.ZeroValueIncludesDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ZeroValueIncludesDescription(fieldValue = "shippingFee", fieldDescription = "name", mandatoryDescription = "Frete Grátis")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "restaurant")
public class Restaurant {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull
    @NotBlank
    @Column(nullable = false)
    private String name;

    //@DecimalMin("0")
    //@ShippingFee
    //@Multiple(number = 5)
    @NotNull
    @PositiveOrZero
    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    //@JsonIgnore
    @Valid //valida em cascata
    @ConvertGroup(to = Groups.KitchenId.class)
    @NotNull
    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
    private Kitchen kitchen;

    @Embedded
    private Address address;

    private Boolean active = Boolean.TRUE;

    private Boolean open = Boolean.FALSE;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updateDate;

    @ManyToMany
    @JoinTable(name = "restaurant_form_payment",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "form_payment_id"))
    private Set<FormPayment> formPayments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurant_person_responsible",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<User> responsible = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    public void activate() {
        setActive(true);
    }

    public void inactivate() {
        setActive(false);
    }

    public void open() {
        setOpen(true);
    }

    public void close() {
        setOpen(false);
    }


    public boolean isOpen() {
        return this.open;
    }

    public boolean isClose() {
        return !isOpen();
    }

    public boolean isInactive() {
        return !isActive();
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean openingAllowed() {
        return isActive() && isClose();
    }

    public boolean activationAllowed() {
        return isInactive();
    }

    public boolean inactivationAllowed() {
        return isActive();
    }

    public boolean closingAllowed() {
        return isOpen();
    }

    public void removeFormPayment(FormPayment formPayment) {
        getFormPayments().remove(formPayment);
    }

    public void addFormPayment(FormPayment formPayment) {
        getFormPayments().add(formPayment);
    }

    public void removeResponsible(User user) {
        getResponsible().remove(user);
    }

    public void addResponsible(User user) {
        getResponsible().add(user);
    }

    public boolean acceptFormPayment(FormPayment formPayment) {
        return getFormPayments().contains(formPayment);
    }

    public boolean notAcceptPaymentForm(FormPayment formPayment) {
        return !acceptFormPayment(formPayment);
    }
}
