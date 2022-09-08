package com.rodrigo.foodapi.domain.repository;

import com.rodrigo.foodapi.domain.model.FormPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormPaymentRepository extends JpaRepository<FormPayment, Long> {

    @Query("select max(updateDate) from FormPayment")
    OffsetDateTime getLastUpdateDate();
}
