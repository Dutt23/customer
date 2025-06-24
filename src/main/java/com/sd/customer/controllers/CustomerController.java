package com.sd.customer.controllers;


import com.sd.customer.dtos.CustomerDTO;
import com.sd.customer.models.Customer;
import com.sd.customer.services.CustomerService;
import com.sd.customer.services.ICustomerService;
import com.sd.customer.services.KafkaProducerService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    private ICustomerService customerService;

    private KafkaProducerService kafkaProducerService;

    private static final String CREATE_CUSTOMER_TOPIC = "customer-created-topic";
    @Autowired
    public CustomerController(final CustomerService customerService, final KafkaProducerService kafkaProducerService) {
        this.customerService = customerService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity<List<Customer>> createCustomer(@Valid @RequestBody List<CustomerDTO> customersReq) {
        List<Customer> customers = customerService.createCustomers(customersReq);
        LOGGER.info("[CustomerController] created {} customers", customers.size());
        this.kafkaProducerService.sendMessageAsync(CREATE_CUSTOMER_TOPIC,customers);
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomer(@RequestParam Map<String, String> allParams, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers(allParams, pageable));
    }
}
