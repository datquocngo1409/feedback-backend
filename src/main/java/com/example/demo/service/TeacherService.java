package com.example.demo.service;

import com.example.demo.model.Teacher;
import com.example.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findById(int id) {
        return teacherRepository.findById(id).get();
    }

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public void deleteById(int id) {
        teacherRepository.deleteById(id);
    }

    public Teacher findByUsername(String username) { return teacherRepository.findByUsername(username);}
}
