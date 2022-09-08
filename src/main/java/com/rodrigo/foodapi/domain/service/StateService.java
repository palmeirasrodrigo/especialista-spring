package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.StateNotFoundException;
import com.rodrigo.foodapi.domain.model.State;
import com.rodrigo.foodapi.domain.repository.StateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StateService {

    public static final String MSG_STATE_IN_USE = "Estado de código %d não pode ser removida, pois está em uso ";
    @Autowired
    private StateRepository stateRepository;

    public List<State> listAll() {
        return stateRepository.findAll();
    }

    public State find(Long stateId) {
        return stateRepository.findById(stateId).orElseThrow(() -> new StateNotFoundException(stateId)
        );
    }

    @Transactional
    public State create(State state) {
        return stateRepository.save(state);
    }

    @Transactional
    public void remove(Long stateId) {
        try {
            stateRepository.deleteById(stateId);
            stateRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(stateId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_STATE_IN_USE, stateId)
            );
        }
    }
}
