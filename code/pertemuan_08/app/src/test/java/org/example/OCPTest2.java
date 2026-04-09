package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.ocp2b.EmailNotifier;
import org.example.ocp2b.Notifier;
import org.example.ocp2b.SMSNotifier;
import org.junit.jupiter.api.Test;

public class OCPTest2 {

    @Test
    void testLegacyNotificationServiceMustBeModifiedForNewChannels() {
        org.example.ocp2a.NotificationService service = new org.example.ocp2a.NotificationService();

        assertDoesNotThrow(() -> service.send("email", "Halo"));
        assertDoesNotThrow(() -> service.send("sms", "Halo"));
        assertThrows(IllegalArgumentException.class, () -> service.send("push", "Halo"));
    }

    @Test
    void testRefactoredNotificationServiceWorksWithExistingNotifiers() {
        org.example.ocp2b.NotificationService service = new org.example.ocp2b.NotificationService();

        assertDoesNotThrow(() -> service.sendNotification(new EmailNotifier(), "Halo"));
        assertDoesNotThrow(() -> service.sendNotification(new SMSNotifier(), "Halo"));
    }

    @Test
    void testCanAddPushNotifierWithoutChangingNotificationService() {
        class PushNotifier implements Notifier {
            private boolean called;

            @Override
            public void send(String message) {
                called = true;
            }
        }

        PushNotifier notifier = new PushNotifier();
        org.example.ocp2b.NotificationService service = new org.example.ocp2b.NotificationService();

        assertDoesNotThrow(() -> service.sendNotification(notifier, "Halo"));
        assertTrue(notifier.called, "Push notifier should be usable without modifying NotificationService");
    }

    @Test
    void testRefactoredServiceDependsOnNotifierAbstraction() throws NoSuchMethodException {
        assertNotNull(org.example.ocp2b.NotificationService.class
                .getDeclaredMethod("sendNotification", Notifier.class, String.class));
    }
}
