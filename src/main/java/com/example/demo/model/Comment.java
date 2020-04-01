package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int idCommented;

    @OneToMany
    private List<Student> studentsLiked;

    @OneToMany
    private List<Teacher> teachersLiked;

    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCommented() {
        return idCommented;
    }

    public void setIdCommented(int idCommented) {
        this.idCommented = idCommented;
    }

    public List<Student> getStudentsLiked() {
        return studentsLiked;
    }

    public void setStudentsLiked(List<Student> studentsLiked) {
        this.studentsLiked = studentsLiked;
    }

    public List<Teacher> getTeachersLiked() {
        return teachersLiked;
    }

    public void setTeachersLiked(List<Teacher> teachersLiked) {
        this.teachersLiked = teachersLiked;
    }
}
