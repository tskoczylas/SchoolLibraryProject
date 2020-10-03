package com.tomsapp.Toms.V2.dto;

import com.tomsapp.Toms.V2.entity.Adress;

import javax.mail.Address;
import javax.validation.constraints.*;
import java.util.Objects;

public class StudentAddressDto {

    int studentId;

    int addressId;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String firstName;
    @NotNull
    @Size(min=2,max = 12, message = "Max 2 , Min 8 digit")
    private String lastName;


    private String email;

    private String confirmEmail;

    @NotNull
    @Size(min=2, message = "Max 2 , Min 8 digit")
    private String password;

    private String confirmPassword;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentAddressDto that = (StudentAddressDto) o;
        return studentId == that.studentId &&
                addressId == that.addressId &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(confirmEmail, that.confirmEmail) &&
                Objects.equals(password, that.password) &&
                Objects.equals(confirmPassword, that.confirmPassword) &&
                Objects.equals(addressFirstLine, that.addressFirstLine) &&
                Objects.equals(addressSecondLine, that.addressSecondLine) &&
                Objects.equals(postCode, that.postCode) &&
                Objects.equals(country, that.country) &&
                Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, addressId, firstName, lastName, email, confirmEmail, password, confirmPassword, addressFirstLine, addressSecondLine, postCode, country, telephone);
    }

    public StudentAddressDto() {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public boolean isPasswordMatch(){
        return this.password.equals(this.confirmPassword);
    }
    public boolean isEmailMatch(){
        return this.email.equals(this.confirmEmail);
    }

    @Override
    public String toString() {
        return "StudentAddressDto{" +
                "studentId=" + studentId +
                ", addressId=" + addressId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", confirmEmail='" + confirmEmail + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", addressFirstLine='" + addressFirstLine + '\'' +
                ", addressSecondLine='" + addressSecondLine + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}