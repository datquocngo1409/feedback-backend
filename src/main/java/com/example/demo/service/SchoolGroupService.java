package com.example.demo.service;

import com.example.demo.model.Lesson;
import com.example.demo.model.SchoolGroup;
import com.example.demo.repository.SchoolGourpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolGroupService {
    @Autowired
    private SchoolGourpRepository schoolGourpRepository;

    public List<SchoolGroup> findAll() {
        return schoolGourpRepository.findAll();
    }

    public SchoolGroup findById(int id) {
        return schoolGourpRepository.findById(id).get();
    }

    public void save(SchoolGroup schoolGroup) {
        schoolGourpRepository.save(schoolGroup);
    }

    public void deleteById(int id) {
        schoolGourpRepository.deleteById(id);
    }
}
