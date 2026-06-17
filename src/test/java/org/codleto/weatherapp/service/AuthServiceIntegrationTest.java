package org.codleto.weatherapp.service;

import org.codleto.weatherapp.entity.Session;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.exception.UserAlreadyExistsException;
import org.codleto.weatherapp.repository.SessionsRepository;
import org.codleto.weatherapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
    @ActiveProfiles("test")
    @Transactional
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionsRepository sessionsRepository;

    @Test
    void save_shouldCreateUserAndSession() {
        UUID sessionId = authService.save("baro", "Password123!", "Password123!");

        User user = userRepository.findByLogin("baro")
                .orElseThrow();

        assertEquals("baro", user.getLogin());

        Session session = sessionsRepository.findById(sessionId)
                .orElseThrow();

        assertEquals(user.getId(), session.getUser().getId());
        assertTrue(session.getExpiresAt().isAfter(LocalDateTime.now()));
    }

    @Test
    void save_withExistingLogin_shouldThrowException() {
        authService.save("baro", "Password123!", "Password123!");

        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.save("baro", "Password123!", "Password123!");
        });
    }
}
