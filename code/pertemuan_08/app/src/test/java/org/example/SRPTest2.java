
package org.example;

import java.lang.reflect.Method;

import org.example.srp2b.EmailSender;
import org.example.srp2b.EmailValidator;
import org.example.srp2b.User;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

public class SRPTest2 {

    @Test
    void testUserHasOnlyDataResponsibility() {
        Method[] methods = User.class.getDeclaredMethods();
        for (Method method : methods) {
            assertTrue(method.getName().startsWith("get") || method.getName().startsWith("set"),
                    "User class should only contain data access methods");
        }
    }

    @Test
    void testEmailValidatorOnlyValidates() {
        try {
            Method method = EmailValidator.class.getDeclaredMethod("isValid", String.class);
            assertEquals(boolean.class, method.getReturnType(), "EmailValidator should return boolean");
        } catch (NoSuchMethodException e) {
            fail("EmailValidator should have isValid(String) method");
        }
    }

    @Test
    void testEmailValidatorDoesNotSendEmail() {
        Method[] methods = EmailValidator.class.getDeclaredMethods();
        for (Method method : methods) {
            assertNotEquals("sendWelcomeEmail", method.getName(), "Validator should not send email");
        }
    }

    @Test
    void testEmailSenderOnlySendsEmail() {
        try {
            Method method = EmailSender.class.getDeclaredMethod("sendWelcomeEmail", String.class);
            assertEquals(void.class, method.getReturnType(), "EmailSender should send email and return void");
        } catch (NoSuchMethodException e) {
            fail("EmailSender should have sendWelcomeEmail(String) method");
        }
    }

    @Test
    void testEachClassHasOnlyOneReasonToChange() {
        // Indirect SRP check by instantiating and using each class separately
        User user = new User();
        EmailValidator validator = new EmailValidator();
        EmailSender sender = new EmailSender();

        assertDoesNotThrow(() -> validator.isValid("test@example.com"));
        assertDoesNotThrow(() -> sender.sendWelcomeEmail("test@example.com"));
        assertDoesNotThrow(user::getEmail);
    }
}
