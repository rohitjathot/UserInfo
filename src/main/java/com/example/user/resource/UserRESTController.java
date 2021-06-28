package com.example.user.resource;

import com.example.user.controller.UserDBController;
import com.example.user.controller.Validation;
import com.example.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//All the REST APIs will be here in this class
@RestController
public class UserRESTController {

    @Autowired
    private UserDBController database;

    @Autowired
    private Validation validation;

    @GetMapping("/")
    public ResponseEntity<ArrayList<User>> getUser() {
        return new ResponseEntity<ArrayList<User>>(database.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User>  getUserById(@PathVariable("id") Integer id) {
        User user = database.getUser(id);
        if (user != null)
            return new ResponseEntity<User>(user,HttpStatus.OK);
        else
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(validation.validate(user) == false){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        user = database.createUser(user);
        if (user != null)
            return new ResponseEntity<User>(user,HttpStatus.CREATED);
        else
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUserById(@RequestBody User user) {
        if(validation.validate(user) == false){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        user = database.updateUser(user);
        if (user != null)
            return new ResponseEntity<User>(user,HttpStatus.CREATED);
        else
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeUserById(@PathVariable("id") Integer id) {
        if(database.removeUser(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}