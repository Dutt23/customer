package com.sd.customer.controllers;


import com.sd.customer.dtos.CustomerDTO;
import com.sd.customer.models.Customer;
import com.sd.customer.services.AddressService;
import com.sd.customer.services.CustomerService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

    private CustomerService customerService;

    private AddressService addressService;

    @Autowired
    public CustomerController(
            final CustomerService customerService,
            final AddressService addressService
    ) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public Customer createCustomer(@Valid @RequestBody List<CustomerDTO> customers) {

        return null;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public Customer getCustomer() {
        return null;
    }
}
