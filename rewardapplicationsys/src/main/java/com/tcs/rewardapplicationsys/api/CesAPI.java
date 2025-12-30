package com.tcs.rewardapplicationsys.api;

import com.tcs.rewardapplicationsys.dto.CesUserDTO;
import com.tcs.rewardapplicationsys.entity.CesUser;
import com.tcs.rewardapplicationsys.exception.RewardException;
import com.tcs.rewardapplicationsys.service.CesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class CesAPI {
    @Autowired
    CesService cesService;
    @Autowired

    Environment env;
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody CesUserDTO cesUser) throws RewardException {
        // Call the service once and capture the return message (e.g., "User added with ID: 101")
        cesService.addCesUser(cesUser);
        String message=env.getProperty("API.User.Added");

        // Return the response with a 201 Created status
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
    @DeleteMapping("/user/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) throws RewardException {
        String message = cesService.deleteByUserName(username);
        return ResponseEntity.ok(message);
    }


}
