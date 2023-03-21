package com.admin.controller;

import com.admin.entity.Admin;
import com.admin.exception.InputNotValidException;
import com.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/save")
    public ResponseEntity<Admin> saveAdmin(@Valid @RequestBody Admin admin) {
        log.info("saveAdmin() method of AdminController is called");

        if(admin.getAdminName() == "" || admin.getPassword() == "") {
            log.warn("fields can not be empty");
            throw new InputNotValidException("Fields can not be empty");
        } else if(!Pattern.matches("[a-zA-Z]+", admin.getAdminName())) {
            log.warn("adminName only contain letters");
            throw new InputNotValidException("adminName only contain letters");
        } else if(!(admin.getPassword().length() >= 8)) {
            log.warn("password should be greater than or equal to 8 character");
            throw new InputNotValidException("password should be greater than or equal to 8 character");
        }

        Admin savedAdmin = adminService.saveAdmin(admin);
        return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Integer id) {
        log.info("getAdminById() method of AdminController is called");
        if(id < 1) {
            log.warn("Id is not valid");
            throw new InputNotValidException("Id id not valid");
        }
        Admin admin = adminService.getAdminById(id);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") Integer id, @RequestBody Admin admin) {
        log.info("updateAdmin() method of AdminController is called");

        if(id < 1) {
            log.warn("Id is not valid");
            throw new InputNotValidException("Id id not valid");
        } else if(admin.getAdminName() == "" || admin.getPassword() == "") {
            log.warn("fields can not be empty");
            throw new InputNotValidException("Fields can not be empty");
        } else if(!Pattern.matches("[a-zA-Z]+", admin.getAdminName())) {
            log.warn("adminName only contain letters");
            throw new InputNotValidException("adminName only contain letters");
        } else if(!(admin.getPassword().length() >= 8)) {
            log.warn("password should be greater than or equal to 8");
            throw new InputNotValidException("password should be greater than or equal to 8");
        }

        Admin updatedAdmin = adminService.updateAdmin(id, admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable("id") Integer id) {
        log.info("deleteAdminById() method of AdminController is called");
        if(id < 1) {
            log.warn("Id is not valid");
            throw new InputNotValidException("Id is not valid");
        }
        adminService.deleteAdminById(id);
        return new ResponseEntity<>("Admin successfully deleted by id: "+id, HttpStatus.OK);
    }
}
