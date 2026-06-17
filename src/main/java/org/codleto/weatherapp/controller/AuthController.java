package org.codleto.weatherapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.codleto.weatherapp.exception.*;
import org.codleto.weatherapp.service.CookieService;
import org.codleto.weatherapp.service.AuthService;

import org.codleto.weatherapp.service.SignOutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieService cookieService;
    private final SignOutService signOutService;

    @GetMapping("/sign-up")
    public String signUp(){
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String confirmPassword,
                         HttpServletResponse response,
                         Model model) {

        try {
            UUID sessionId = authService.save(username, password, confirmPassword);

            response.addCookie(cookieService.createCookie(sessionId));

            return "redirect:/index";

        } catch (EmptyFieldException e) {

            model.addAttribute("formError", e.getMessage());
            model.addAttribute("username", username);

            return "sign-up-with-errors";

        } catch (UserAlreadyExistsException | InvalidLoginException e) {

            model.addAttribute("usernameError", e.getMessage());
            model.addAttribute("username", username);

            return "sign-up-with-errors";

        } catch (InvalidPasswordException e) {

            model.addAttribute("passwordError", e.getMessage());
            model.addAttribute("username", username);

            return "sign-up-with-errors";

        } catch (PasswordsDoNotMatchException e) {

            model.addAttribute("confirmPasswordError", e.getMessage());
            model.addAttribute("username", username);

            return "sign-up-with-errors";
        }
    }

    @PostMapping("/sign-out")
    public String signOut (HttpServletRequest request,
                           HttpServletResponse response) {
        signOutService.signOut(request);

        response.addCookie(cookieService.deleteCookie());

        return "redirect:/sign-in";
    }
}
