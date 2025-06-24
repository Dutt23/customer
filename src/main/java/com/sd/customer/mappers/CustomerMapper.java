package com.sd.customer.mappers;


import com.sd.customer.dtos.CustomerDTO;
import com.sd.customer.models.Address;
import com.sd.customer.models.Customer;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "mobileNumber", target = "customerNumber")
    @Mapping(source = "address", target = "addresses")
    Customer toEntity(CustomerDTO dto);

    @Mapping(source = "customerNumber", target = "mobileNumber")
    @Mapping(source = "addresses", target = "address")
    CustomerDTO toDto(Customer entity);

    List<Customer> toEntityList(List<CustomerDTO> dtoList);
    List<CustomerDTO> toDtoList(List<Customer> entityList);

    @AfterMapping
    default void linkAddresses(@MappingTarget Customer customer) {
        if (customer.getAddresses() != null) {
            for (Address address : customer.getAddresses()) {
                address.setCustomer(customer);
            }
        }
    }
}
