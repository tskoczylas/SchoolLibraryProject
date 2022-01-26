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


    private String addressFirstLine;


    private String addressSecondLine;


    private String postCode;


    private String country;


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

    public Student getAdressStudent() {
        return adressStudent;
    }

    public void setAdressStudent(Student adressStudent) {
        this.adressStudent = adressStudent;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", addressFirstLine='" + addressFirstLine + '\'' +
                ", addressSecondLine='" + addressSecondLine + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adressStudent=" + adressStudent +
                '}';
    }
}
