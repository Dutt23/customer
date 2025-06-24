package com.sd.customer.mappers;

import com.sd.customer.dtos.AddressDTO;
import com.sd.customer.models.Address;
import com.sd.customer.models.Customer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "type", target = "addressType")
    @Mapping(source = "address1", target = "address")
    @Mapping(source = "address2", target = "street")
    @Mapping(source = "zipCode", target = "zip")
    @Mapping(target = "customer", ignore = true) // Ignore back-reference
    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressDTO dto);

    @Mapping(source = "addressType", target = "type")
    @Mapping(source = "address", target = "address1")
    @Mapping(source = "street", target = "address2")
    @Mapping(source = "zip", target = "zipCode")
    AddressDTO toDto(Address entity);
}
