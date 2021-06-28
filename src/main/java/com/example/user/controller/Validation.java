package com.example.user.controller;

import com.example.user.model.User;
import org.springframework.stereotype.Controller;

//This class will be used for validating inputs
@Controller
public class Validation {

    private static Validation instance;

    public static Validation getInstance(){
        return instance==null?instance = new Validation():instance;
    }

    private Validation() {
        //Nothing here
    }

    public boolean validate(User user) {
        return validate(user.getAge()) && validate(user.getFirstName()) && validate(user.getLastName());
    }

    private boolean validate(Integer age) {
        return age!=null && age>0;
    }

    private boolean validate(String name) {
        return name!=null && name.matches("^[a-zA-Z]+$");
    }
}
