package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.model.Demand;
import com.rodrigo.foodapi.domain.repository.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderFlowService {

    @Autowired
    private DemandService demandService;

    @Autowired
    private DemandRepository demandRepository;

    @Transactional
    public void confirm(String demandCode) {
        Demand demand = demandService.find(demandCode);
        demand.confirm();

        demandRepository.save(demand);
    }

    @Transactional
    public void cancel(String demandCode) {
        Demand demand = demandService.find(demandCode);
        demand.cancel();

        demandRepository.save(demand);
    }

    @Transactional
    public void delivered(String demandCode) {
        Demand demand = demandService.find(demandCode);
        demand.delivered();
    }
}
