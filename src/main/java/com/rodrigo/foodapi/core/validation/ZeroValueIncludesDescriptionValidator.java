package com.rodrigo.foodapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ZeroValueIncludesDescriptionValidator implements ConstraintValidator<ZeroValueIncludesDescription, Object> {

    private String fieldValue;
    private String fieldDescription;
    private String mandatoryDescription;


    @Override
    public void initialize(ZeroValueIncludesDescription constraintAnnotation) {
        this.fieldValue = constraintAnnotation.fieldValue();
        this.fieldDescription = constraintAnnotation.fieldDescription();
        this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), fieldValue)
                    .getReadMethod().invoke(o); //reflection

            String description = (String) BeanUtils.getPropertyDescriptor(o.getClass(), fieldDescription)
                    .getReadMethod().invoke(o);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && description != null) {
                valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }

            return valid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

}
