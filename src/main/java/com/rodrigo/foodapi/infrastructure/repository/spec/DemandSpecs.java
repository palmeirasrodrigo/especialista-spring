package com.rodrigo.foodapi.infrastructure.repository.spec;

import com.rodrigo.foodapi.domain.model.Demand;
import com.rodrigo.foodapi.domain.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class DemandSpecs {

    public static Specification<Demand> userFilter(OrderFilter filter){
        return (root, query, builder) -> {
            if(Demand.class.equals(query.getResultType())){
                root.fetch("restaurant").fetch("kitchen");
                root.fetch("client");
            }

            var predicates = new ArrayList<Predicate>();

            if(filter.getClientId() != null){
                predicates.add(builder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreateAt() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createAt"),
                        filter.getCreateAt()));
            }

            if (filter.getCreateEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createAt"),
                        filter.getCreateEnd()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
