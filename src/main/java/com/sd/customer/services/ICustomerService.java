package com.sd.customer.services;

import com.sd.customer.dtos.CustomerDTO;
import com.sd.customer.models.Customer;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    List<Customer> createCustomers(List<CustomerDTO> customerDTOS);
    List<Customer> getCustomers(Map<String, String> attributes, Pageable pageable);
}
