package com.rodrigo.foodapi.infrastructure.repository;

import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.repository.RestaurantRepository;
import com.rodrigo.foodapi.domain.repository.RestaurantRepositoryQueries;
import com.rodrigo.foodapi.infrastructure.repository.spec.RestaurantSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant>find(String name, BigDecimal startingFee, BigDecimal finalFee){
        var jpql = new StringBuilder();
        jpql.append("from Restaurant where 0 = 0 ");

        var parameters = new HashMap<String, Object>();

        if (StringUtils.hasLength(name)) {
            jpql.append("and name like :name ");
            parameters.put("name", "%" + name + "%");
        }

        if (startingFee != null) {
            jpql.append("and shippingFee >= :startingFee ");
            parameters.put("startingFee", startingFee);
        }

        if (finalFee != null) {
            jpql.append("and shippingFee <= :finalFee ");
            parameters.put("finalFee", finalFee);
        }

        TypedQuery<Restaurant> query = manager
                .createQuery(jpql.toString(), Restaurant.class);

        parameters.forEach(query::setParameter);

        return query.getResultList();
    }

    @Override
    public List<Restaurant> findByCriteria(String name, BigDecimal startingFee, BigDecimal finalFee) {

        var builder = manager.getCriteriaBuilder();

        var criteria = builder.createQuery(Restaurant.class);
        var root = criteria.from(Restaurant.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasLength(name)) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (startingFee != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("shippingFee"), startingFee));
        }

        if (finalFee != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("shippingFee"), finalFee));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        var query = manager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> restaurantFreeShipping(String name) {
        return restaurantRepository.findAll(RestaurantSpecs.freeShipping().and(RestaurantSpecs.similarName(name)));
    }
}
