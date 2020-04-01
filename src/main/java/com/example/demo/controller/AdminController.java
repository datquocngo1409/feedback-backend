package com.example.demo.controller;

import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import java.util.List;
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

@Controller
public class AdminController {
    @Autowired
    public AdminService adminService;

    //API trả về List Admin.
    @RequestMapping(value = "/admins", method = RequestMethod.GET)
    public ResponseEntity<List<Admin>> listAllAdmins() {
        List<Admin> admins = adminService.findAll();
        if (admins.isEmpty()) {
            return new ResponseEntity<List<Admin>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Admin>>(admins, HttpStatus.OK);
    }

    //API trả về Admin có ID trên url.
    @RequestMapping(value = "/admins/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") int id) {
        System.out.println("Fetching Admin with id " + id);
        Admin admin = adminService.findById(id);
        if (admin == null) {
            System.out.println("Admin with id " + id + " not found");
            return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Admin>(admin, HttpStatus.OK);
    }

    //API tạo một Admin mới.
    @RequestMapping(value = "/admins", method = RequestMethod.POST)
    public ResponseEntity<Void> createAdmin(@RequestBody Admin admin, UriComponentsBuilder ucBuilder) {
        Admin check = adminService.findByUsername(admin.getUsername());
        if (check != null) {
            System.out.println("Username is existed");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        System.out.println("Creating Admin " + admin.getUsername());
        adminService.save(admin);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/admins/{id}").buildAndExpand(admin.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //API cập nhật một Admin với ID trên url.
    @RequestMapping(value = "/admins/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") int id, @RequestBody Admin admin) {
        System.out.println("Updating Admin " + id);

        Admin currentAdmin = adminService.findById(id);

        if (currentAdmin == null) {
            System.out.println("Admin with id " + id + " not found");
            return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
        }

        currentAdmin = admin;

        adminService.save(currentAdmin);
        return new ResponseEntity<Admin>(currentAdmin, HttpStatus.OK);
    }

    //API xóa một Admin với ID trên url.
    @RequestMapping(value = "/admins/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Admin> deleteAdmin(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Admin with id " + id);

        Admin admin = adminService.findById(id);
        if (admin == null) {
            System.out.println("Unable to delete. Admin with id " + id + " not found");
            return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
        }

        adminService.deleteById(id);
        return new ResponseEntity<Admin>(HttpStatus.NO_CONTENT);
    }
}
