package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.BusinessException;
import com.rodrigo.foodapi.domain.exception.DemandNotFoundException;
import com.rodrigo.foodapi.domain.model.*;
import com.rodrigo.foodapi.domain.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DemandService {

    public static final String MSG_CITY_IN_USE = "demand de código %d não pode ser removida, pois está em uso";
    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FormPaymentService formPaymentService;

    @Autowired
    private ProductService productService;

    public Page<Demand> listAll(Specification<Demand> demandSpecification, Pageable pageable) {
        return demandRepository.findAll(demandSpecification, pageable);
    }

    public Demand find(String demandCode) {
        return demandRepository.findByCode(demandCode).orElseThrow(() -> new DemandNotFoundException(demandCode));
    }

    @Transactional
    public Demand issue(Demand demand) {
        validateOrder(demand);
        validateItems(demand);

        demand.setShippingFee(demand.getRestaurant().getShippingFee());
        demand.calculateTotalValue();
        return demandRepository.save(demand);
    }

    private void validateOrder(Demand demand) {
        City city = cityService.find(demand.getAddress().getCity().getId());
        User client = userService.find(demand.getClient().getId());
        Restaurant restaurant = restaurantService.find(demand.getRestaurant().getId());
        FormPayment formPayment = formPaymentService.find(demand.getFormPayments().getId());

        demand.getAddress().setCity(city);
        demand.setClient(client);
        demand.setRestaurant(restaurant);
        demand.setFormPayments(formPayment);

        if (restaurant.notAcceptPaymentForm(formPayment)) {
            throw new BusinessException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formPayment.getDescription()));
        }
    }

    private void validateItems(Demand demand) {
        demand.getItems().forEach(item -> {
            Product product = productService.find(
                    demand.getRestaurant().getId(), item.getProduct().getId());

            item.setDemand(demand);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }
}
