package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String name;

    @OneToMany
    private List<Student> studentsCreated;

    @OneToMany
    private List<Teacher> teachersCreated;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudentsCreated() {
        return studentsCreated;
    }

    public void setStudentsCreated(List<Student> studentsCreated) {
        this.studentsCreated = studentsCreated;
    }

    public List<Teacher> getTeachersCreated() {
        return teachersCreated;
    }

    public void setTeachersCreated(List<Teacher> teachersCreated) {
        this.teachersCreated = teachersCreated;
    }
}
