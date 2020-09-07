package com.tomsapp.Toms.V2.entity;

import org.springframework.scheduling.support.SimpleTriggerContext;

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

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "students_role",
            joinColumns =@JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn( name = "student_id")
    )
    List<Students> students;

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

    public List<Students> getStudents() {
        return students;
    }

    public void setStudents(List<Students> students) {
        this.students = students;
    }
}
