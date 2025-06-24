package com.sd.customer.dtos;

import jakarta.validation.constraints.NotBlank;

public class AddressDTO {
    @NotBlank
    private String type;
    @NotBlank
    private String address1;

    private String address2;
    private String city;
    private String state;
    private String zipCode;

    // Getters and Setters
    public String getType() { return type; }
    public void setType(String type ) { this.type = type; }
    public void setAddress1(String address1) {  this.address1 = address1; }
    public String getAddress1() { return address1; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getAddress2() { return this.address2; }

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
