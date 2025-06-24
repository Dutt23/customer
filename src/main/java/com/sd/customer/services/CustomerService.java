package com.sd.customer.services;

import com.sd.customer.dtos.CustomerDTO;
import com.sd.customer.exceptions.InvalidParameterException;
import com.sd.customer.mappers.CustomerMapper;
import com.sd.customer.models.Customer;
import com.sd.customer.repositories.CustomerRepository;
import com.sd.customer.utils.SpecificationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService implements ICustomerService {

    private static final Logger LOGGER = LogManager.getLogger(CustomerService.class);
    private CustomerMapper customerMapper;

    private CustomerRepository customerRepository;
    @Autowired
    public CustomerService (CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers(Map<String, String> attributes, Pageable pageable) {
        Specification<Customer> spec = SpecificationUtils.buildSpecificationFromMap(attributes);
        try {
            return customerRepository.findAll(spec, pageable).getContent();
        } catch(InvalidDataAccessApiUsageException e) {
            LOGGER.error(e);
            throw new InvalidParameterException("Please check the parameters sent");
        }
    }
    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public List<Customer> createCustomers(List<CustomerDTO> customerDTOS) {

        List<Customer> customers = this.customerRepository.saveAll(this.customerMapper.toEntityList(customerDTOS));
        LOGGER.info("[CustomerService] inserted all {} customers", customerDTOS.size());
        return customers;
    }

//    Method to be used if bulk creation fails , because of one bad user.
@Recover
public List<Customer> recoverAfterBulkInsert(Exception e, List<CustomerDTO> customerDTOS) {
    LOGGER.info("[CustomerService] Bulk insert failure : ", e);
    LOGGER.info("[CustomerService] Attempting to insert one by one : ", e);
    List<Customer> failedCustomers = new ArrayList<>();
    List<Customer> savedCustomers = new ArrayList<>();
    for (CustomerDTO dto : customerDTOS) {
        try {
            Customer customer = this.customerMapper.toEntity(dto);
            Customer saved = this.customerRepository.save(customer);
            savedCustomers.add(saved);
        } catch (Exception ex) {
            // Log or collect failed DTOs
            failedCustomers.add(this.customerMapper.toEntity(dto));
        }
    }
    // Optionally, log or handle failedCustomers as needed
    if (!failedCustomers.isEmpty()) {
        // You can throw an exception, log, or return partial results
        LOGGER.error("Failed to save the following customers: " + failedCustomers);
    }
    LOGGER.info("[CustomerService] tried to insert {} customers succeded in inserting {} ", customerDTOS.size(), customerDTOS.size() - failedCustomers.size());
    return savedCustomers;
}
}
