package com.tomsapp.Toms.V2.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

public class Adress {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String addressFirstLine;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String addressSecondLine;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String postCode;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String country;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String telephone;

    @OneToOne
    Student adressStudent;

    public Adress() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getAddressFirstLine() {
        return addressFirstLine;
    }

    public void setAddressFirstLine(@NonNull String addressFirstLine) {
        this.addressFirstLine = addressFirstLine;
    }

    @NonNull
    public String getAddressSecondLine() {
        return addressSecondLine;
    }

    public void setAddressSecondLine(@NonNull String addressSecondLine) {
        this.addressSecondLine = addressSecondLine;
    }

    @NonNull
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(@NonNull String postCode) {
        this.postCode = postCode;
    }

    @NonNull
    public String getCountry() {
        return country;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    @NonNull
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(@NonNull String telephone) {
        this.telephone = telephone;
    }

    public Student getAdressStudent() {
        return adressStudent;
    }

    public void setAdressStudent(Student adressStudent) {
        this.adressStudent = adressStudent;
    }
}
