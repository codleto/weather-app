package org.codleto.weatherapp.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.entity.Session;
import org.codleto.weatherapp.entity.User;
import org.codleto.weatherapp.exception.UnauthorizedException;
import org.codleto.weatherapp.repository.SessionsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final CookieService cookieService;
    private final SessionsRepository sessionsRepository;

    public User getCurrentUser (HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        Optional<Cookie> sessionCookie = cookieService.findSessionCookie(cookies);

        if (sessionCookie.isEmpty()) {
            throw new UnauthorizedException();
        }

        UUID sessionId;

        try {
            sessionId = UUID.fromString((sessionCookie.get().getValue()));
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }

        Optional<Session> session = sessionsRepository.findById(sessionId);

        if (session.isEmpty()) {
            throw new UnauthorizedException();
        }

        return session.get().getUser();
    }
}
