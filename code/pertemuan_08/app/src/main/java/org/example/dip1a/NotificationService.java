package org.example.dip1a;

public class NotificationService {
    private final EmailSender sender = new EmailSender();

    public void notifyUser(String message) {
        sender.send(message);
    }
}
