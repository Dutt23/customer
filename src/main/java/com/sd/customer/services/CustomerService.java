package com.sd.customer.services;

import com.sd.customer.dtos.CustomerDTO;
import com.sd.customer.mappers.CustomerMapper;
import com.sd.customer.models.Customer;
import com.sd.customer.repositories.CustomerRepository;
import com.sd.customer.utils.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {

    private CustomerMapper customerMapper;

    private CustomerRepository customerRepository;
    @Autowired
    public CustomerService (CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers(Map<String, String> attributes, Pageable pageable) {
        Specification<Customer> spec = SpecificationUtils.buildSpecificationFromMap(attributes);
        return customerRepository.findAll(spec, pageable).getContent();
    }

    public List<Customer> createCustomers(List<CustomerDTO> customerDTOS) {
        List<Customer> customers =  this.customerMapper.toEntityList(customerDTOS);
        return this.customerRepository.saveAll(customers);
    }
}
