package com.example.demo.controller;

import com.example.demo.model.CommentRep;
import com.example.demo.service.CommentRepService;
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
public class CommentRepController {
    @Autowired
    public CommentRepService commentRepService;

    //API trả về List CommentRep.
    @RequestMapping(value = "/commentreps", method = RequestMethod.GET)
    public ResponseEntity<List<CommentRep>> listAllCommentReps() {
        List<CommentRep> commentReps = commentRepService.findAll();
        if (commentReps.isEmpty()) {
            return new ResponseEntity<List<CommentRep>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<CommentRep>>(commentReps, HttpStatus.OK);
    }

    //API trả về CommentRep có ID trên url.
    @RequestMapping(value = "/commentReps/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentRep> getCommentRepById(@PathVariable("id") int id) {
        System.out.println("Fetching CommentRep with id " + id);
        CommentRep commentRep = commentRepService.findById(id);
        if (commentRep == null) {
            System.out.println("CommentRep with id " + id + " not found");
            return new ResponseEntity<CommentRep>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CommentRep>(commentRep, HttpStatus.OK);
    }

    //API tạo một CommentRep mới.
    @RequestMapping(value = "/commentReps", method = RequestMethod.POST)
    public ResponseEntity<Void> createCommentRep(@RequestBody CommentRep commentRep, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating CommentRep " + commentRep.getId());
        commentRepService.save(commentRep);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/commentReps/{id}").buildAndExpand(commentRep.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một CommentRep với ID trên url.
    @RequestMapping(value = "/commentReps/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CommentRep> updateCommentRep(@PathVariable("id") int id, @RequestBody CommentRep commentRep) {
        System.out.println("Updating CommentRep " + id);

        CommentRep currentCommentRep = commentRepService.findById(id);

        if (currentCommentRep == null) {
            System.out.println("CommentRep with id " + id + " not found");
            return new ResponseEntity<CommentRep>(HttpStatus.NOT_FOUND);
        }

        currentCommentRep = commentRep;

        commentRepService.save(currentCommentRep);
        return new ResponseEntity<CommentRep>(currentCommentRep, HttpStatus.OK);
    }

    //API xóa một CommentRep với ID trên url.
    @RequestMapping(value = "/commentReps/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommentRep> deleteCommentRep(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting CommentRep with id " + id);

        CommentRep commentRep = commentRepService.findById(id);
        if (commentRep == null) {
            System.out.println("Unable to delete. CommentRep with id " + id + " not found");
            return new ResponseEntity<CommentRep>(HttpStatus.NOT_FOUND);
        }

        commentRepService.deleteById(id);
        return new ResponseEntity<CommentRep>(HttpStatus.NO_CONTENT);
    }
}
