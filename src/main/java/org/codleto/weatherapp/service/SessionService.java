package org.codleto.weatherapp.service;

import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.entity.Session;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.repository.SessionsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    @Value("${session.expiration-hours}")
    private long sessionExpirationHours;

    private final SessionsRepository sessionsRepository;

    @Transactional
    public UUID createSession(User user){

        UUID sessionId = UUID.randomUUID();
        Session session = new Session(
                sessionId,
                user,
                LocalDateTime.now().plusHours(sessionExpirationHours));

        sessionsRepository.save(session);

        return sessionId;
    }

    @Transactional(readOnly = true)
    public boolean isSessionValid(String cookieId){

        UUID sessionId;

        try {
            sessionId = UUID.fromString(cookieId);
        } catch (IllegalArgumentException e) {
            return false;
        }

        Optional<Session> session = sessionsRepository.findById(sessionId);

        if (session.isEmpty()){
            return false;
        }

        return session.get().getExpiresAt().isAfter(LocalDateTime.now());
    }
}
