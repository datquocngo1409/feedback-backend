package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.model.CommentRep;
import com.example.demo.repository.CommentRepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentRepService {
    @Autowired
    private CommentRepRepository commentRepRepository;

    public List<CommentRep> findAll() {
        return commentRepRepository.findAll();
    }

    public CommentRep findById(int id) {
        return commentRepRepository.findById(id).get();
    }

    public void save(CommentRep commentRep) {
        commentRepRepository.save(commentRep);
    }

    public void deleteById(int id) {
        commentRepRepository.deleteById(id);
    }
}
