package com.rodrigo.foodapi.domain.repository;

import com.rodrigo.foodapi.domain.model.Demand;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandRepository extends CustomJpaRepository<Demand, Long>, JpaSpecificationExecutor<Demand> {

    Optional<Demand> findByCode(String code);

    @Query("from Demand p join fetch p.client join fetch p.restaurant r join fetch r.kitchen ")
    List<Demand> findAll();

}
