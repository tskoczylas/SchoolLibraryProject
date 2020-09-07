package com.tomsapp.Toms.V2.entity;

import jdk.internal.instrumentation.InstrumentationMethod;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

public class Adress {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
    int id;
    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    @Column(name = "street")
    private String adress;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    @Column(name = "post_code")
    private String postCode;

    @NonNull
    @NotBlank
    @Size(min = 5, max = 15)
    @Column(name = "country")
    private String coutry;
    @ManyToOne()
    @JoinColumn(name = "student_id")
    Students adressStudents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Adress() {
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public Students getAdressStudents() {
        return adressStudents;
    }

    public void setAdressStudents(Students adressStudents) {
        this.adressStudents = adressStudents;
    }
}
