package com.sd.customer.controllers;


import com.sd.customer.constants.Constants;
import com.sd.customer.dtos.CustomerCreateRequest;
import com.sd.customer.models.Customer;
import com.sd.customer.services.CustomerService;
import com.sd.customer.services.ICustomerService;
import com.sd.customer.services.KafkaProducerService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    private ICustomerService customerService;

    private KafkaProducerService kafkaProducerService;
    @Autowired
    public CustomerController(final CustomerService customerService, final KafkaProducerService kafkaProducerService) {
        this.customerService = customerService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @RequestMapping(value = Constants.CustomerRouteConstants.ROUTE, method = RequestMethod.POST)
    public ResponseEntity<List<Customer>> createCustomer(@Valid @RequestBody List<CustomerCreateRequest> customersReq) {
        List<Customer> customers = customerService.createCustomers(customersReq.stream().map(req -> req.getCustomer()).collect(Collectors.toList()));
        LOGGER.info("[CustomerController] created {} customers", customers.size());
        this.kafkaProducerService.sendMessageAsync(Constants.KafkaConstants.CREATE_CUSTOMER_TOPIC,customers);
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @RequestMapping(value = Constants.CustomerRouteConstants.ROUTE, method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomer(@RequestParam Map<String, String> allParams, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers(allParams, pageable));
    }
}
