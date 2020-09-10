package com.tomsapp.Toms.V2.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Enumerated(EnumType.STRING)
    RoleEnum role;

    public Role(RoleEnum role) {
        this.role = role;

    }

    public Role(int id, RoleEnum role) {
        this.id = id;
        this.role = role;
    }

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "students_role",
            joinColumns =@JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn( name = "student_id")
    )
    List<Student> students;

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
