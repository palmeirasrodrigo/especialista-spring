package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.BusinessException;
import com.rodrigo.foodapi.domain.exception.CityNotFoundException;
import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.StateNotFoundException;
import com.rodrigo.foodapi.domain.model.City;
import com.rodrigo.foodapi.domain.model.State;
import com.rodrigo.foodapi.domain.repository.CityRepository;
import com.rodrigo.foodapi.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    public static final String MSG_CITY_IN_USE = "Cidade de código %d não pode ser removida, pois está em uso";
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateService stateService;

    public List<City> listAll() {
        return cityRepository.findAll();
    }

    public City find(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
    }

    @Transactional
    public City create(City city) {
        try {
            State state = stateService.find(city.getState().getId());
            city.setState(state);
            return cityRepository.save(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void remove(Long cityId) {
        try {
            cityRepository.deleteById(cityId);
            cityRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(cityId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_CITY_IN_USE, cityId)
            );
        }
    }

}
