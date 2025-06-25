package com.sd.customer.dtos;

import jakarta.validation.Valid;

import java.util.List;

public class CustomerCreateRequest {
    @Valid
    private CustomerDTO customer;

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
}
