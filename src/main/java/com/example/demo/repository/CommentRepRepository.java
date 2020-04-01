package com.example.demo.repository;

import com.example.demo.model.CommentRep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepRepository extends JpaRepository<CommentRep, Integer> {
}
