package org.codleto.weatherapp.util;

import org.codleto.weatherapp.exception.*;
import org.codleto.weatherapp.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class Validation {

    private final UserRepository userRepository;

    public Validation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validationSignUp(String login, String password, String confirmPassword) {
        if (login == null || password == null || confirmPassword == null ||
                login.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            throw new EmptyFieldException();
        }

        if (login.length() < 3 || login.length() > 30) {
            throw new InvalidLoginException(
                    "Login must be between 3 and 30 characters."
            );
        }

        if (password.length() < 8 || password.length() > 100) {
            throw new InvalidPasswordException(
                    "Password must be between 8 and 100 characters."
            );
        }

        validatePassword(password);
        validateLogin(login);

        if (userRepository.existsByLogin(login)) {
            throw new UserAlreadyExistsException();
        }

        if (!password.equals(confirmPassword)) {
            throw new PasswordsDoNotMatchException();
        }
    }

    public void validationSignIn(String login, String password) {

        if (login == null || password == null || login.isBlank() || password.isBlank()) {
            throw new EmptyFieldException();
        }
    }

    private void validateLogin(String login) {
        if (!login.matches("^[a-zA-Z0-9_-]+$")) {

            throw new InvalidLoginException(
                    "Login can contain only English letters, digits, '-' and '_'."
            );
        }
    }

    private void validatePassword(String password) {

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialSymbol = false;

        for (char c : password.toCharArray()) {

            if (c == ' ') {
                throw new InvalidPasswordException(
                        "Password cannot contain spaces."
                );
            }

            if (c >= 'A' && c <= 'Z') {
                hasUppercase = true;
            }

            if (c >= 'a' && c <= 'z') {
                hasLowercase = true;
            }

            if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if (!Character.isLetterOrDigit(c)) {
                hasSpecialSymbol = true;
            }

            boolean isEnglishLetter =
                    (c >= 'a' && c <= 'z') ||
                            (c >= 'A' && c <= 'Z');

            boolean isAllowedSymbol =
                    !Character.isLetterOrDigit(c);

            if (!isEnglishLetter &&
                    !Character.isDigit(c) &&
                    !isAllowedSymbol) {

                throw new InvalidPasswordException(
                        "Password can contain only English letters, digits and special symbols."
                );
            }
        }

        if (!hasUppercase) {
            throw new InvalidPasswordException(
                    "Password must contain at least one uppercase letter."
            );
        }

        if (!hasLowercase) {
            throw new InvalidPasswordException(
                    "Password must contain at least one lowercase letter."
            );
        }

        if (!hasDigit) {
            throw new InvalidPasswordException(
                    "Password must contain at least one digit."
            );
        }

        if (!hasSpecialSymbol) {
            throw new InvalidPasswordException(
                    "Password must contain at least one special symbol."
            );
        }
    }
}

