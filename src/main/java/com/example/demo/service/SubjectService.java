package com.example.demo.service;

import com.example.demo.model.Subject;
import com.example.demo.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findById(int id) {
        return subjectRepository.findById(id).get();
    }

    public void save(Subject subject) {
        subjectRepository.save(subject);
    }

    public void deleteById(int id) {
        subjectRepository.deleteById(id);
    }

    public Subject findByUsername(String subjectCode) { return subjectRepository.findBySubjectCode(subjectCode);}
}
