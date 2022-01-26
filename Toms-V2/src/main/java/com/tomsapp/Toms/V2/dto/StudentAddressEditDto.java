package com.tomsapp.Toms.V2.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class StudentAddressEditDto {
    int addressId;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String firstName;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String lastName;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String addressFirstLine;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String addressSecondLine;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String postCode;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String country;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String telephone;

    public StudentAddressEditDto() {
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddressFirstLine() {
        return addressFirstLine;
    }

    public void setAddressFirstLine(String addressFirstLine) {
        this.addressFirstLine = addressFirstLine;
    }

    public String getAddressSecondLine() {
        return addressSecondLine;
    }

    public void setAddressSecondLine(String addressSecondLine) {
        this.addressSecondLine = addressSecondLine;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentAddressEditDto that = (StudentAddressEditDto) o;
        return addressId == that.addressId &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(addressFirstLine, that.addressFirstLine) &&
                Objects.equals(addressSecondLine, that.addressSecondLine) &&
                Objects.equals(postCode, that.postCode) &&
                Objects.equals(country, that.country) &&
                Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, firstName, lastName, addressFirstLine, addressSecondLine, postCode, country, telephone);
    }
}
