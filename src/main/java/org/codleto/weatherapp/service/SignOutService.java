package org.codleto.weatherapp.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.repository.SessionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SignOutService {

    private final CookieService cookieService;
    private final SessionsRepository sessionsRepository;

    @Transactional
    public void signOut(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        Optional<Cookie> sessionCookie = cookieService.findSessionCookie(cookies);

        if (sessionCookie.isEmpty()) {
            return;
        }

        UUID sessionId;

        try {
            sessionId =UUID.fromString(sessionCookie.get().getValue());
        } catch (IllegalArgumentException e) {
            return;
        }

        sessionsRepository.deleteById(sessionId);
    }
}
