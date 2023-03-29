package com.becoder.controller;

import com.becoder.model.UserDtls;
import com.becoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
//userservice is used to auto save user data so autowired and service layer passed here
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/createUser")
    //post is use here so getting data from user - fetch all user data
    // model body attribute to bind all user data , who passed the data

    //Servlet concept use this things
    // like HttpSession to show data on frontend not in terminal like already exist
    public String createuser(@ModelAttribute UserDtls user, HttpSession session) {
         // to check data in terminal
        // System.out.println(user);


        //check email exist or not and if-else conditions ,
        // userservice-impl-userrepo all in declare this things
        boolean f = userService.checkEmail(user.getEmail());

        if (f) {
            session.setAttribute("msg", "Email Id alreday exists");
        }

        else {
            UserDtls userDtls = userService.createUser(user);
            if (userDtls != null) {
                session.setAttribute("msg", "Register Sucessfully");
            } else {
                session.setAttribute("msg", "Something wrong on server");
            }
        }
//after save data redirect so issue will have resolve no cache availble
        return "redirect:/register";
    }

}
