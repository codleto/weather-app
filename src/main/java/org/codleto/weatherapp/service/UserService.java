package org.codleto.weatherapp.service;

import lombok.AllArgsConstructor;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    public User save(String name, String password){
        validation(name, password);

        User user = new User(name, password);

        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long lon){
        return userRepository.findById(lon);
    }

    private void validation(String name, String password) {
        if (name == null || password == null) {
            throw new RuntimeException();
        }
    }
}
