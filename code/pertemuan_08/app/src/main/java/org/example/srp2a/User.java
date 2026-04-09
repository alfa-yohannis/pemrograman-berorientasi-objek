package org.example.srp2a;

import java.util.regex.Pattern;

public class User {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public boolean isValidEmail() {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public void sendWelcomeEmail() {
        if (!isValidEmail()) {
            throw new IllegalStateException("Email tidak valid");
        }
    }
}
