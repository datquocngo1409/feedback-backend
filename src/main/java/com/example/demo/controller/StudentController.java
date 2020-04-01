package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
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
public class StudentController {
    @Autowired
    public StudentService studentService;

    //API trả về List Student.
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> listAllStudents() {
        List<Student> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }

    //API trả về Student có ID trên url.
    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        System.out.println("Fetching Student with id " + id);
        Student student = studentService.findById(id);
        if (student == null) {
            System.out.println("Student with id " + id + " not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    //API tạo một Student mới.
    @RequestMapping(value = "/studetns", method = RequestMethod.POST)
    public ResponseEntity<Void> createStudent(@RequestBody Student student, UriComponentsBuilder ucBuilder) {
        Student check = studentService.findByUsername(student.getUsername());
        if (check != null) {
            System.out.println("Username is existed");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        System.out.println("Creating Student " + student.getId());
        studentService.save(student);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/students/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Student với ID trên url.
    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Student> updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        System.out.println("Updating Student " + id);

        Student currentStudents = studentService.findById(id);

        if (currentStudents == null) {
            System.out.println("Student with id " + id + " not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }

        currentStudents = student;

        studentService.save(currentStudents);
        return new ResponseEntity<Student>(currentStudents, HttpStatus.OK);
    }

    //API xóa một Student với ID trên url.
    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Student with id " + id);

        Student student = studentService.findById(id);
        if (student == null) {
            System.out.println("Unable to delete. Student with id " + id + " not found");
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }

        studentService.deleteById(id);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
}
