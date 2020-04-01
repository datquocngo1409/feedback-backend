package com.example.demo.controller;

import com.example.demo.model.Lesson;
import com.example.demo.service.LessonService;
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
public class LessonController {
    @Autowired
    public LessonService lessonService;

    //API trả về List Lesson.
    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public ResponseEntity<List<Lesson>> listAllLessons() {
        List<Lesson> lessons = lessonService.findAll();
        if (lessons.isEmpty()) {
            return new ResponseEntity<List<Lesson>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Lesson>>(lessons, HttpStatus.OK);
    }

    //API trả về Lesson có ID trên url.
    @RequestMapping(value = "/lessons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lesson> getLessonById(@PathVariable("id") int id) {
        System.out.println("Fetching Lesson with id " + id);
        Lesson lesson = lessonService.findById(id);
        if (lesson == null) {
            System.out.println("Lesson with id " + id + " not found");
            return new ResponseEntity<Lesson>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Lesson>(lesson, HttpStatus.OK);
    }

    //API tạo một Lesson mới.
    @RequestMapping(value = "/lessons", method = RequestMethod.POST)
    public ResponseEntity<Void> createLesson(@RequestBody Lesson lesson, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Lesson " + lesson.getId());
        lessonService.save(lesson);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/lessons/{id}").buildAndExpand(lesson.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Lesson với ID trên url.
    @RequestMapping(value = "/lessons/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Lesson> updateLesson(@PathVariable("id") int id, @RequestBody Lesson lesson) {
        System.out.println("Updating Lesson " + id);

        Lesson currentLesson = lessonService.findById(id);

        if (currentLesson == null) {
            System.out.println("Lesson with id " + id + " not found");
            return new ResponseEntity<Lesson>(HttpStatus.NOT_FOUND);
        }

        currentLesson = lesson;

        lessonService.save(currentLesson);
        return new ResponseEntity<Lesson>(currentLesson, HttpStatus.OK);
    }

    //API xóa một Lesson với ID trên url.
    @RequestMapping(value = "/lessons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Lesson> deleteLesson(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Lesson with id " + id);

        Lesson lesson = lessonService.findById(id);
        if (lesson == null) {
            System.out.println("Unable to delete. Lesson with id " + id + " not found");
            return new ResponseEntity<Lesson>(HttpStatus.NOT_FOUND);
        }

        lessonService.deleteById(id);
        return new ResponseEntity<Lesson>(HttpStatus.NO_CONTENT);
    }
}
