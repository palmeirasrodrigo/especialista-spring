package com.rodrigo.foodapi.domain.repository;

import com.rodrigo.foodapi.domain.model.Kitchen;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitchenRepository extends CustomJpaRepository<Kitchen, Long> {
    List<Kitchen> findByName(String name);
    List<Kitchen> findByNameContaining(String name);
    boolean existsByName(String name);
}
