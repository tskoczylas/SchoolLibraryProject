package com.tomsapp.Toms.V2.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

public class Adress {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String street;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String postCode;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    private String coutry;
    @ManyToOne()
    Student adressStudent;

    public Adress(int id, @NonNull @NotBlank @Size(min = 5, max = 15) String street, @NonNull @NotBlank @Size(min = 5, max = 15) String postCode, @NonNull @NotBlank @Size(min = 5, max = 15) String coutry, Student adressStudent) {
        this.id = id;
        this.street = street;
        this.postCode = postCode;
        this.coutry = coutry;
        this.adressStudent = adressStudent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Adress() {
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCoutry() {
        return coutry;
    }

    public void setCoutry(String coutry) {
        this.coutry = coutry;
    }

    public Student getAdressStudent() {
        return adressStudent;
    }

    public void setAdressStudent(Student adressStudent) {
        this.adressStudent = adressStudent;
    }
}
