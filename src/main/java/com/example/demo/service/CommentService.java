package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.CommentRep;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(int id) {
        return commentRepository.findById(id).get();
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }
}
