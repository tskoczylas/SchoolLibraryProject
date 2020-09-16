package com.tomsapp.Toms.V2.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private  String password;
    private  boolean isEnabled;

    @ManyToMany (fetch = FetchType.EAGER)
  /*  @JoinTable(name = "students_role",
            joinColumns =@JoinColumn(name= "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")) */
   private List<Role> rolesSet;

    @OneToOne(mappedBy = "adressStudent",cascade = CascadeType.ALL)
   private Adress adresses;

    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, String username, String password, boolean isEnabled, List<Role> rolesSet) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.rolesSet = rolesSet;
    }

    public Student(String firstName, String lastName, String username, String email, String password, boolean isEnabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;

    }

    public Student(String firstName, String lastName, String username, String email, String password, boolean isEnabled, List<Role> rolesSet) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.rolesSet = rolesSet;
    }

    public Student() {
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<Role> getRolesSet() {
        return rolesSet;
    }

    public void setRolesSet(List<Role> rolesSet) {
        this.rolesSet = rolesSet;
    }

    public Adress getAdresses() {
        return adresses;
    }

    public void setAdresses(Adress adresses) {
        this.adresses = adresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", rolesSet=" + rolesSet +
                ", adresses=" + adresses +
                '}';
    }
}
