package com.rodrigo.foodapi.domain.repository;

import com.rodrigo.foodapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries,
        JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join r.kitchen")
    List<Restaurant> findAll();

    List<Restaurant> findByShippingFeeBetween(BigDecimal startingFee, BigDecimal finalFee);

    List<Restaurant> findByNameContainingAndKitchenId(String name, Long kitchenId);

    Optional<Restaurant> findFirstByNameContaining(String name);

    List<Restaurant> findTop2ByNameContaining(String name);

    int countByKitchenId(Long kitchenId);

    @Query("from Restaurant where name like %:name% and kitchen.id = :id")
    List<Restaurant> searchName(String name, @Param("id") Long kitchenId);

    List<Restaurant> searchByNameAndKitchenId(String name, Long kitchenId); //orm.xml


}
