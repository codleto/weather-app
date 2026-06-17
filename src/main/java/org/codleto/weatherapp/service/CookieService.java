package org.codleto.weatherapp.service;

import jakarta.servlet.http.Cookie;
import org.codleto.weatherapp.common.CookieNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class CookieService {

    @Value("${session.expiration-hours}")
    private long sessionExpirationHours;

    public Cookie createCookie(UUID sessionId){

        Cookie cookie = new Cookie(CookieNames.SESSION_ID, sessionId.toString());

        cookie.setPath("/");
        cookie.setMaxAge((int) Duration.ofHours(sessionExpirationHours).getSeconds());
        cookie.setHttpOnly(true);

        return cookie;
    }

    public Optional<Cookie> findSessionCookie(Cookie[] cookies) {

        if (cookies == null) {
            return Optional.empty();
        }

         for (Cookie cookie : cookies) {
             if (CookieNames.SESSION_ID.equals(cookie.getName())) {
                 return Optional.of(cookie);
             }
         }

         return Optional.empty();
    }

    public Cookie deleteCookie() {

        Cookie cookie = new Cookie("SESSION_ID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
