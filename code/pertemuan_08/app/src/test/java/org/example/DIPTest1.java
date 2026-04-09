package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.example.dip1b.MessageSender;
import org.junit.jupiter.api.Test;

public class DIPTest1 {

    @Test
    void testLegacyNotificationServiceDependsOnConcreteEmailSender() throws NoSuchFieldException {
        Field senderField = org.example.dip1a.NotificationService.class.getDeclaredField("sender");

        assertEquals(org.example.dip1a.EmailSender.class, senderField.getType());
    }

    @Test
    void testRefactoredNotificationServiceDependsOnMessageSenderAbstraction()
            throws NoSuchFieldException, NoSuchMethodException {
        Field senderField = org.example.dip1b.NotificationService.class.getDeclaredField("sender");

        assertEquals(MessageSender.class, senderField.getType());
        assertNotNull(org.example.dip1b.NotificationService.class.getDeclaredConstructor(MessageSender.class));
    }

    @Test
    void testRefactoredNotificationServiceSupportsDependencyInjection() {
        class RecordingSender implements MessageSender {
            private boolean called;

            @Override
            public void send(String message) {
                called = true;
            }
        }

        RecordingSender sender = new RecordingSender();
        org.example.dip1b.NotificationService service = new org.example.dip1b.NotificationService(sender);

        service.notifyUser("Halo");

        assertTrue(sender.called, "Injected sender should be used by NotificationService");
    }
}
