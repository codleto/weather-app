package org.codleto.weatherapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.codleto.weatherapp.common.CookieNames;
import org.codleto.weatherapp.service.SessionService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();

        if (path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.equals("/sign-in")
                || path.equals("/sign-up")){

            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookie = request.getCookies();

        if (isAuthenticated(cookie)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        response.sendRedirect("/sign-in");
    }

    private boolean isAuthenticated(Cookie[] cookies){

        Optional<Cookie> sessionCookie = findSessionCookie(cookies);

        if(sessionCookie.isEmpty()){
           return false;
        }

        String cookie = sessionCookie.get().getValue();

        return sessionService.isSessionValid(cookie);
    }

    private Optional<Cookie> findSessionCookie(Cookie[] cookies){

        if (cookies == null){
            return Optional.empty();
        }

        for (Cookie cookie : cookies){
            if (CookieNames.SESSION_ID.equals(cookie.getName())) {
                return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }
}
