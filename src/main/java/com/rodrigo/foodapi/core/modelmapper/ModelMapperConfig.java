package com.rodrigo.foodapi.core.modelmapper;

import com.rodrigo.foodapi.api.model.request.ItemDemandRequest;
import com.rodrigo.foodapi.api.model.response.AddressResponse;
import com.rodrigo.foodapi.api.model.response.ItemDemandResponse;
import com.rodrigo.foodapi.domain.model.Address;
import com.rodrigo.foodapi.domain.model.ItemDemand;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemDemandRequest.class,ItemDemand.class)
                .addMappings(mapper -> mapper.skip(ItemDemand::setId));

        var addressToAddressResponse = modelMapper.createTypeMap(Address.class, AddressResponse.class);

        addressToAddressResponse.addMapping(
                addressSrc -> addressSrc.getCity().getState().getName(),
                (addressRespDest, value) -> addressRespDest.getCity().setState((String) value));

        return modelMapper;
    }
}
