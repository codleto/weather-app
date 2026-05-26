package org.codleto.weatherapp.controller;

import lombok.AllArgsConstructor;
import org.codleto.weatherapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class Registration {

    private final UserService userService;

    @GetMapping("/")
    public String hello(){
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(String username,
                         String password,
                         String confirmPassword){

        System.out.println(userService.save(username, password));

        return "redirect:/index";
    }
}
