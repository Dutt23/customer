package com.sd.customer.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = { CustomerController.class })
public class CustomerExceptionController {
}
