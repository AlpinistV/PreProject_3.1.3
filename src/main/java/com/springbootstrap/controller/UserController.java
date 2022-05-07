package com.springbootstrap.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String userInfo() {
        return "select";
    }
}