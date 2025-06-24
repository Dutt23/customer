package com.sd.customer.dtos;

import jakarta.validation.constraints.NotBlank;

public class AddressDTO {
    @NotBlank
    private String addressType;
    @NotBlank
    private String address;
    private String city;
    private String state;
    private String zipCode;

    // Getters and Setters
    public String getAddressType() { return addressType; }
    public void setAddressType(String addressType) { this.addressType = addressType; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state != null ? state : ""; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode != null ? zipCode : ""; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getStateZipCode() {
        return this.getState() + " " + this.getZipCode();
    }
}
