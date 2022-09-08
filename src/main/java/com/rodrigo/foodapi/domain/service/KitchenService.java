package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.KitchenNotFoundException;
import com.rodrigo.foodapi.domain.model.Kitchen;
import com.rodrigo.foodapi.domain.repository.KitchenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KitchenService {

    public static final String KITCHEN_IN_USE = "Cozinha de código %d não pode ser removida, pois está em uso";
    @Autowired
    private KitchenRepository kitchenRepository;

    public Page<Kitchen> listAll(Pageable pageable) {
        return kitchenRepository.findAll(pageable);
    }

    public Kitchen find(Long kitchenId) {
        return kitchenRepository.findById(kitchenId).orElseThrow(
                () -> new KitchenNotFoundException(kitchenId)
        );
    }

    @Transactional
    public Kitchen create(Kitchen kitchen) {
        return kitchenRepository.save(kitchen);
    }

    @Transactional
    public void remove(Long kitchenId) {
        try {
            kitchenRepository.deleteById(kitchenId);
            kitchenRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new KitchenNotFoundException(kitchenId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(KITCHEN_IN_USE, kitchenId)
            );
        }
    }
}
