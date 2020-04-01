package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.model.Subject;
import com.example.demo.service.SubjectService;
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
public class SubjectController {
    @Autowired
    public SubjectService subjectService;

    //API trả về List Subject.
    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ResponseEntity<List<Subject>> listAllSubjects() {
        List<Subject> subjects = subjectService.findAll();
        if (subjects.isEmpty()) {
            return new ResponseEntity<List<Subject>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Subject>>(subjects, HttpStatus.OK);
    }

    //API trả về Subject có ID trên url.
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> getSubjectById(@PathVariable("id") int id) {
        System.out.println("Fetching Subject with id " + id);
        Subject subject = subjectService.findById(id);
        if (subject == null) {
            System.out.println("Subject with id " + id + " not found");
            return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Subject>(subject, HttpStatus.OK);
    }

    //API tạo một Subject mới.
    @RequestMapping(value = "/subjects", method = RequestMethod.POST)
    public ResponseEntity<Void> createSubject(@RequestBody Subject subject, UriComponentsBuilder ucBuilder) {
        Subject check = subjectService.findByUsername(subject.getSubjectCode());
        if (check != null) {
            System.out.println("Username is existed");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        System.out.println("Creating Subject " + subject.getId());
        subjectService.save(subject);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/subjects/{id}").buildAndExpand(subject.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Subject với ID trên url.
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Subject> updateSubject(@PathVariable("id") int id, @RequestBody Subject subject) {
        System.out.println("Updating Subject " + id);

        Subject currentSubject = subjectService.findById(id);

        if (currentSubject == null) {
            System.out.println("Subject with id " + id + " not found");
            return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);
        }

        currentSubject = subject;

        subjectService.save(currentSubject);
        return new ResponseEntity<Subject>(currentSubject, HttpStatus.OK);
    }

    //API xóa một Subject với ID trên url.
    @RequestMapping(value = "/subjects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Subject> deleteSubject(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Subject with id " + id);

        Subject subject = subjectService.findById(id);
        if (subject == null) {
            System.out.println("Unable to delete. Subject with id " + id + " not found");
            return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);
        }

        subjectService.deleteById(id);
        return new ResponseEntity<Subject>(HttpStatus.NO_CONTENT);
    }
}
