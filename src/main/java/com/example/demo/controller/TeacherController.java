package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.service.TeacherService;
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
public class TeacherController {
    @Autowired
    public TeacherService teacherService;

    //API trả về List Teacher.
    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public ResponseEntity<List<Teacher>> listAllTeachers() {
        List<Teacher> teachers = teacherService.findAll();
        if (teachers.isEmpty()) {
            return new ResponseEntity<List<Teacher>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Teacher>>(teachers, HttpStatus.OK);
    }

    //API trả về Teacher có ID trên url.
    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") int id) {
        System.out.println("Fetching Teacher with id " + id);
        Teacher teacher = teacherService.findById(id);
        if (teacher == null) {
            System.out.println("Teacher with id " + id + " not found");
            return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
    }

    //API tạo một Teacher mới.
    @RequestMapping(value = "/teachers", method = RequestMethod.POST)
    public ResponseEntity<Void> createTeacher(@RequestBody Teacher teacher, UriComponentsBuilder ucBuilder) {
        Teacher check = teacherService.findByUsername(teacher.getUsername());
        if (check != null) {
            System.out.println("Username is existed");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        System.out.println("Creating Teacher " + teacher.getId());
        teacherService.save(teacher);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/teachers/{id}").buildAndExpand(teacher.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Teacher với ID trên url.
    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") int id, @RequestBody Teacher teacher) {
        System.out.println("Updating Teacher " + id);

        Teacher currentTeacher = teacherService.findById(id);

        if (currentTeacher == null) {
            System.out.println("Teacher with id " + id + " not found");
            return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
        }

        currentTeacher = teacher;

        teacherService.save(currentTeacher);
        return new ResponseEntity<Teacher>(currentTeacher, HttpStatus.OK);
    }

    //API xóa một Teacher với ID trên url.
    @RequestMapping(value = "/teachers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Teacher with id " + id);

        Teacher teacher = teacherService.findById(id);
        if (teacher == null) {
            System.out.println("Unable to delete. Teacher with id " + id + " not found");
            return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
        }

        teacherService.deleteById(id);
        return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
    }
}
