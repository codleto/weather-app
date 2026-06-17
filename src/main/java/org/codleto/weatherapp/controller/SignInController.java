package org.codleto.weatherapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.exception.EmptyFieldException;
import org.codleto.weatherapp.exception.InvalidLoginOrPasswordException;
import org.codleto.weatherapp.service.CookieService;
import org.codleto.weatherapp.service.SignInService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;
    private final CookieService cookieService;

    @GetMapping("/sign-in")
    public String signIn(){
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestParam String login,
                         @RequestParam String password,
                         HttpServletResponse response,
                         Model model){

        try {
            UUID sessionId = signInService.signIn(login, password);

            response.addCookie(cookieService.createCookie(sessionId));

            return "redirect:/index";

        } catch (EmptyFieldException | InvalidLoginOrPasswordException e) {

            model.addAttribute("error", e.getMessage());
            model.addAttribute("login", login);

            return "sign-in-with-errors";
        }
    }
}
