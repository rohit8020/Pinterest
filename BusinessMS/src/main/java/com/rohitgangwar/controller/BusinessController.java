package com.rohitgangwar.controller;

import com.rohitgangwar.exception.BusinessException;
import com.rohitgangwar.service.BusinessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping
public class BusinessController {

    @Autowired
    BusinessService businessService;

    @GetMapping("/add-business/{userId}")
    public ResponseEntity<String> addBusiness(@PathVariable Long userId){

        try {
            String msg = businessService.addBusiness(userId);
            return new ResponseEntity<String>(msg, HttpStatus.CREATED);
        }
        catch(BusinessException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }


    }
}

