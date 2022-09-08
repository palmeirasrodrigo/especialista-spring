package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.exception.EntityInUseException;
import com.rodrigo.foodapi.domain.exception.FormPaymentNotFoundException;
import com.rodrigo.foodapi.domain.model.FormPayment;
import com.rodrigo.foodapi.domain.repository.FormPaymentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormPaymentService {

    public static final String MSG_FORM_PAYMENT_IN_USE = "Forma de pagamento de código %d não pode ser removida, pois está em uso ";
    @Autowired
    private FormPaymentRepository formPaymentRepository;

    public List<FormPayment> listAll() {
        return formPaymentRepository.findAll();
    }

    public FormPayment find(Long formPaymentId) {
        return formPaymentRepository.findById(formPaymentId).orElseThrow(() -> new FormPaymentNotFoundException(formPaymentId));
    }

    @Transactional
    public FormPayment create(FormPayment formPayment) {
        return formPaymentRepository.save(formPayment);
    }

    @Transactional
    public void remove(Long formPaymentId) {
        try {
            formPaymentRepository.deleteById(formPaymentId);
            formPaymentRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormPaymentNotFoundException(formPaymentId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_FORM_PAYMENT_IN_USE, formPaymentId)
            );
        }
    }
}
