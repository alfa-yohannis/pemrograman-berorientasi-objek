package org.example.ocp2b;

public class NotificationService {
    public void sendNotification(Notifier notifier, String message) {
        notifier.send(message);
    }
}
