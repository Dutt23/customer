package com.sd.customer.dtos;


import jakarta.validation.Valid;

public class CustomerWrapperDTO {
        @Valid
        private List<CustomerDTO customer;

        // Getters and Setters

        public CustomerDTO getCustomer() { return customer; }
        public void setCustomer(CustomerDTO customer) { this.customer = customer; }
    }
