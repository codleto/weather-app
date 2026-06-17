package org.codleto.weatherapp.service;

import org.codleto.weatherapp.entity.Session;
import org.codleto.weatherapp.entity.User;
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
class SessionServiceIntegrationTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionsRepository sessionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createSession_shouldCreateValidSession() {
        User user = userRepository.save(new User("baro", "123"));

        UUID sessionId = sessionService.createSession(user);

        assertTrue(sessionService.isSessionValid(sessionId.toString()));
    }

    @Test
    void expiredSession_shouldBeInvalid() {
        User user = userRepository.save(new User("baro", "123"));

        UUID sessionId = UUID.randomUUID();

        Session expiredSession = new Session(
                sessionId,
                user,
                LocalDateTime.now().minusHours(1)
        );

        sessionsRepository.save(expiredSession);

        assertFalse(sessionService.isSessionValid(sessionId.toString()));
    }

    @Test
    void invalidUuid_shouldBeInvalid() {
        assertFalse(sessionService.isSessionValid("not-uuid"));
    }

    @Test
    void unknownSession_shouldBeInvalid() {
        UUID randomSessionId = UUID.randomUUID();

        assertFalse(sessionService.isSessionValid(randomSessionId.toString()));
    }
}
