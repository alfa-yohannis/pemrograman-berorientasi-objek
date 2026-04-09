package org.example.srp2b;

public class EmailSender {

    public void sendWelcomeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email tidak boleh kosong");
        }
    }
}
