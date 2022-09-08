package com.rodrigo.foodapi.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigo.foodapi.domain.exception.*;
import com.rodrigo.foodapi.domain.model.*;
import com.rodrigo.foodapi.domain.repository.KitchenRepository;
import com.rodrigo.foodapi.domain.repository.RestaurantRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantService {

    public static final String MSG_RESTAURANT_IN_USE = "Restaurante de código %d não pode ser removida, pois está em uso";
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private CityService cityService;

    @Autowired
    private FormPaymentService formPaymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmartValidator validator;

    public List<Restaurant> listAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant find(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RestaurantNotFoundException(restaurantId)
        );
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        try {
            Kitchen kitchen = kitchenService.find(restaurant.getKitchen().getId());
            City city = cityService.find(restaurant.getAddress().getCity().getId());
            restaurant.setKitchen(kitchen);
            restaurant.getAddress().setCity(city);
            return restaurantRepository.save(restaurant);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Transactional
    public Restaurant partialUpdate(Long restaurantId, Map<String, Object> fields, HttpServletRequest request) {
        Restaurant restaurantActual = find(restaurantId);
        merge(fields, restaurantActual, request);
        validate(restaurantActual);
        return create(restaurantActual);
    }

    @Transactional
    public void remove(Long restaurantId) {
        try {
            restaurantRepository.deleteById(restaurantId);
            restaurantRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantNotFoundException(restaurantId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_RESTAURANT_IN_USE, restaurantId)
            );
        }
    }

    @Transactional
    public void activate(List<Long> restaurantIds){
        restaurantIds.forEach(this::activate);
    }

    @Transactional
    public void inactivate(List<Long> restaurantIds){
        restaurantIds.forEach(this::inactivate);
    }

    @Transactional
    public void activate(Long restaurantId){
        Restaurant actualRestaurant = find(restaurantId);
        actualRestaurant.activate(); //faz o update automático pq está em um estado gerenciado
    }

    @Transactional
    public void inactivate(Long restaurantId){
        Restaurant actualRestaurant = find(restaurantId);
        actualRestaurant.inactivate();
    }

    @Transactional
    public void disassociateFormPayment(Long restaurantId, Long formPaymentId){
        Restaurant restaurant = find(restaurantId);
        FormPayment formPayment = formPaymentService.find(formPaymentId);

        restaurant.removeFormPayment(formPayment);
    }
    @Transactional
    public void associateFormPayment(Long restaurantId, Long formPaymentId) {
        Restaurant restaurant = find(restaurantId);
        FormPayment formPayment = formPaymentService.find(formPaymentId);

        restaurant.addFormPayment(formPayment);
    }

    @Transactional
    public void open(Long restaurantId) {
        Restaurant restaurant = find(restaurantId);
        restaurant.open();
    }

    @Transactional
    public void close(Long restaurantId) {
        Restaurant restaurant = find(restaurantId);
        restaurant.close();
    }

    @Transactional
    public void disassociateResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = find(restaurantId);
        User user = userService.find(userId);
        restaurant.removeResponsible(user);
    }

    @Transactional
    public void associateResponsible(Long restaurantId, Long userId) {
        Restaurant restaurant = find(restaurantId);
        User user = userService.find(userId);
        restaurant.addResponsible(user);
    }

    private void validate(Restaurant restaurant) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, "restaurant");
        validator.validate(restaurant, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private static void merge(Map<String, Object> fields, Restaurant restaurantTarget, HttpServletRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurant originRestaurant = objectMapper.convertValue(fields, Restaurant.class);

            fields.forEach((propertyName, propertyValue) -> {
                Field field = ReflectionUtils.findField(Restaurant.class, propertyName);
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, originRestaurant);

                ReflectionUtils.setField(field, restaurantTarget, newValue);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }


}
