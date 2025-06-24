package com.sd.customer.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CustomerDTO {
    @NotBlank
    private String firstName;
    private String lastName;
    @DecimalMin("0.0")
    private double spendingLimit;
    @NotBlank
    private String mobileNumber;
    @Valid
    private List<AddressDTO> address;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public double getSpendingLimit() { return spendingLimit; }
    public void setSpendingLimit(double spendingLimit) { this.spendingLimit = spendingLimit; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public List<AddressDTO> getAddress() { return address; }
    public void setAddress(List<AddressDTO> address) { this.address = address; }
}
