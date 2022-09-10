package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.assembler.city.CityRequestDisassembler;
import com.rodrigo.foodapi.api.v1.assembler.demand.DemandAssembler;
import com.rodrigo.foodapi.api.v1.assembler.demand.DemandRequestDisassembler;
import com.rodrigo.foodapi.api.v1.assembler.demand.DemandResumeAssembler;
import com.rodrigo.foodapi.api.v1.model.request.DemandRequest;
import com.rodrigo.foodapi.api.v1.model.response.DemandResponse;
import com.rodrigo.foodapi.api.v1.model.response.DemandResumeResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.DemandControllerOpenApi;
import com.rodrigo.foodapi.core.data.PageWrapper;
import com.rodrigo.foodapi.core.data.PageableTranslator;
import com.rodrigo.foodapi.domain.exception.BusinessException;
import com.rodrigo.foodapi.domain.exception.EntityNotFoundException;
import com.rodrigo.foodapi.domain.model.Demand;
import com.rodrigo.foodapi.domain.model.User;
import com.rodrigo.foodapi.domain.filter.OrderFilter;
import com.rodrigo.foodapi.domain.service.DemandService;
import com.rodrigo.foodapi.infrastructure.repository.spec.DemandSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/demands", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemandController implements DemandControllerOpenApi {

    @Autowired
    private DemandService demandService;

    @Autowired
    private DemandAssembler demandAssembler;

    @Autowired
    private DemandRequestDisassembler demandRequestDisassembler;

    @Autowired
    private DemandResumeAssembler demandResumeAssembler;

    @Autowired
    private CityRequestDisassembler cityRequestDisassembler;

    @Autowired
    private PagedResourcesAssembler<Demand> pagedResourcesAssembler;

    @Override
    @GetMapping
    public PagedModel<DemandResumeResponse> search(OrderFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        pageable = translatePageable(pageable);
        Page<Demand> demands = demandService.listAll(DemandSpecs.userFilter(filter), pageable);
        demands = new PageWrapper<>(demands, pageable);

        return pagedResourcesAssembler.toModel(demands, demandResumeAssembler);
    }

//    @GetMapping()
//    public MappingJacksonValue listAll(@RequestParam(required = false) String fields) {
//        List<Demand> demands = demandService.listAll();
//        List<DemandResumeResponse> demandResumeResponses = demandResumeAssembler.toCollectionModel(demands);
//        MappingJacksonValue demandsWrapper = new MappingJacksonValue(demandResumeResponses);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("demandFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(fields)){
//            filterProvider.addFilter("demandFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//        }
//
//        demandsWrapper.setFilters(filterProvider);
//
//        return demandsWrapper;
//    }

    @Override
    @GetMapping("/{demandCode}")
    public DemandResponse find(@PathVariable String demandCode) {
        return demandAssembler.toModel(demandService.find(demandCode));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DemandResponse create(@Valid @RequestBody DemandRequest demandRequest) {
        try {
            Demand newDemand = demandRequestDisassembler.toDomainObject(demandRequest);

            // TODO pegar usuário autenticado
            newDemand.setClient(new User());
            newDemand.getClient().setId(1L);

            newDemand = demandService.issue(newDemand);

            return demandAssembler.toModel(newDemand);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    private Pageable translatePageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "code", "code",
                "subtotal", "subtotal",
                "shippingFee", "shippingFee",
                "amount", "amount",
                "createAt", "createAt",
                "restaurant.name", "restaurant.name",
                "restaurant.id", "restaurant.id",
                "client.id", "client.id",
                "client.name", "client.name"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}
