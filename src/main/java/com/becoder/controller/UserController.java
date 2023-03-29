package com.becoder.controller;

import com.becoder.model.UserDtls;
import com.becoder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

//for spring security we make usercontroller
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;


    //If we use model attribute annotation so we can use this data anywhere
    @ModelAttribute

    //Add in last module after login work this and how we get data from principal
    private void userDetails(Model m, Principal p) {
        String email = p.getName();
        UserDtls user = userRepo.findByEmail(email);

        m.addAttribute("user", user);

    }
//After user anyone can access so only / slash
    @GetMapping("/")
    public String home() {
        return "user/home";
    }

}
