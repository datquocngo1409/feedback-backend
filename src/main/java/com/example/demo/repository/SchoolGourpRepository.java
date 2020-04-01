package com.example.demo.repository;

import com.example.demo.model.SchoolGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolGourpRepository extends JpaRepository<SchoolGroup, Integer> {
}
