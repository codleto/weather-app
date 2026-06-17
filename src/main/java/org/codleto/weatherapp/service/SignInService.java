package org.codleto.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.exception.InvalidLoginOrPasswordException;
import org.codleto.weatherapp.repository.UserRepository;
import org.codleto.weatherapp.util.Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignInService {

    private final Validation validation;
    private final UserRepository userRepository;
    private final SessionService sessionService;

    @Transactional
    public UUID signIn(String login, String password){

        validation.validationSignIn(login, password);

        Optional<User> optionalUser = userRepository.findByLogin(login);

        if (optionalUser.isEmpty()){
            throw new InvalidLoginOrPasswordException();
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)){
            throw new InvalidLoginOrPasswordException();
        }

        return sessionService.createSession(user);
    }
}
