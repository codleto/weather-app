package org.codleto.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.repository.UserRepository;
import org.codleto.weatherapp.util.Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SessionService sessionService;
    private final UserRepository userRepository;
    private final Validation validation;

    @Transactional
    public UUID save(String login, String password, String confirmPassword){

        validation.validationSignUp(login, password, confirmPassword);

        User newUser = createUser(login, password);

        return sessionService.createSession(newUser);
    }

    private User createUser(String login, String password){
        User user = new User(login, password);

        return userRepository.save(user);
    }
}
