package org.example.ocp2a;

public class NotificationService {

    public void send(String channel, String message) {
        if ("email".equals(channel)) {
            return;
        } else if ("sms".equals(channel)) {
            return;
        }

        throw new IllegalArgumentException("Saluran notifikasi tidak didukung: " + channel);
    }
}
