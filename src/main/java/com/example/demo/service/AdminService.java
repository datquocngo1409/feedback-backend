package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(int id) {
        return adminRepository.findById(id).get();
    }

    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    public void deleteById(int id) {
        adminRepository.deleteById(id);
    }

    public Admin findByUsername(String username) { return adminRepository.findByUsername(username);}
}
