package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    public CommentService commentService;

    //API trả về List Comment.
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listAllComments() {
        List<Comment> comments = commentService.findAll();
        if (comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    //API trả về Comment có ID trên url.
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") int id) {
        System.out.println("Fetching Comment with id " + id);
        Comment comment = commentService.findById(id);
        if (comment == null) {
            System.out.println("Comment with id " + id + " not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    //API tạo một Comment mới.
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public ResponseEntity<Void> createComment(@RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Comment " + comment.getId());
        commentService.save(comment);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/comments/{id}").buildAndExpand(comment.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Comment với ID trên url.
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Comment> updateComment(@PathVariable("id") int id, @RequestBody Comment comment) {
        System.out.println("Updating Comment " + id);

        Comment currentComment = commentService.findById(id);

        if (currentComment == null) {
            System.out.println("Comment with id " + id + " not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }

        currentComment = comment;

        commentService.save(currentComment);
        return new ResponseEntity<Comment>(currentComment, HttpStatus.OK);
    }

    //API xóa một Comment với ID trên url.
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Comment with id " + id);

        Comment comment = commentService.findById(id);
        if (comment == null) {
            System.out.println("Unable to delete. Comment with id " + id + " not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }

        commentService.deleteById(id);
        return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
    }
}
