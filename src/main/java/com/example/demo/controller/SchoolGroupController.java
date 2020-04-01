package com.example.demo.controller;

import com.example.demo.model.SchoolGroup;
import com.example.demo.service.SchoolGroupService;
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
public class SchoolGroupController {
    @Autowired
    public SchoolGroupService schoolGroupService;

    //API trả về List SchoolGroup.
    @RequestMapping(value = "/schoolGroup", method = RequestMethod.GET)
    public ResponseEntity<List<SchoolGroup>> listAllSchoolGroup() {
        List<SchoolGroup> schoolGroups = schoolGroupService.findAll();
        if (schoolGroups.isEmpty()) {
            return new ResponseEntity<List<SchoolGroup>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<SchoolGroup>>(schoolGroups, HttpStatus.OK);
    }

    //API trả về SchoolGroup có ID trên url.
    @RequestMapping(value = "/schoolGroups/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SchoolGroup> getSchoolGroupById(@PathVariable("id") int id) {
        System.out.println("Fetching SchoolGroup with id " + id);
        SchoolGroup schoolGroup = schoolGroupService.findById(id);
        if (schoolGroup == null) {
            System.out.println("SchoolGroup with id " + id + " not found");
            return new ResponseEntity<SchoolGroup>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SchoolGroup>(schoolGroup, HttpStatus.OK);
    }

    //API tạo một SchoolGroup mới.
    @RequestMapping(value = "/schoolGroups", method = RequestMethod.POST)
    public ResponseEntity<Void> createSchoolGroup(@RequestBody SchoolGroup schoolGroup, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating SchoolGroup " + schoolGroup.getId());
        schoolGroupService.save(schoolGroup);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/schoolGroups/{id}").buildAndExpand(schoolGroup.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một SchoolGroup với ID trên url.
    @RequestMapping(value = "/schoolGroups/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SchoolGroup> updateSchoolGroup(@PathVariable("id") int id, @RequestBody SchoolGroup schoolGroup) {
        System.out.println("Updating SchoolGroup " + id);

        SchoolGroup currentSchoolGroup = schoolGroupService.findById(id);

        if (currentSchoolGroup == null) {
            System.out.println("SchoolGroup with id " + id + " not found");
            return new ResponseEntity<SchoolGroup>(HttpStatus.NOT_FOUND);
        }

        currentSchoolGroup = schoolGroup;

        schoolGroupService.save(currentSchoolGroup);
        return new ResponseEntity<SchoolGroup>(currentSchoolGroup, HttpStatus.OK);
    }

    //API xóa một SchoolGroup với ID trên url.
    @RequestMapping(value = "/schoolGroups/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<SchoolGroup> deleteSchoolGroup(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting SchoolGroup with id " + id);

        SchoolGroup schoolGroup = schoolGroupService.findById(id);
        if (schoolGroup == null) {
            System.out.println("Unable to delete. SchoolGroup with id " + id + " not found");
            return new ResponseEntity<SchoolGroup>(HttpStatus.NOT_FOUND);
        }

        schoolGroupService.deleteById(id);
        return new ResponseEntity<SchoolGroup>(HttpStatus.NO_CONTENT);
    }
}
