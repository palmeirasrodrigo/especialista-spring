package com.rodrigo.foodapi.infrastructure.service.query;

import com.rodrigo.foodapi.domain.filter.DailySaleFilter;
import com.rodrigo.foodapi.domain.model.Demand;
import com.rodrigo.foodapi.domain.model.StatusDemand;
import com.rodrigo.foodapi.domain.model.dto.DailySale;
import com.rodrigo.foodapi.domain.service.SaleQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> dailySalesConsultation(DailySaleFilter filter, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySale.class);
        var root = query.from(Demand.class);
        var predicates = new ArrayList<Predicate>();

        var functionConvertTzCreateAt = builder.function(
                "convert_tz", Date.class, root.get("createAt"),
                builder.literal("+00:00"), builder.literal(timeOffset));

        var functionDateCreateAt = builder.function(
                "date", Date.class, functionConvertTzCreateAt);

        var selection = builder.construct(DailySale.class,
                functionDateCreateAt,
                builder.count(root.get("id")),
                builder.sum(root.get("amount")));

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

        predicates.add(root.get("status").in(
                StatusDemand.CONFIRMADO, StatusDemand.ENTREGUE));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateCreateAt);

        return manager.createQuery(query).getResultList();
    }
}
