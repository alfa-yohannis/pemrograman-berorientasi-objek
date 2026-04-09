package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;

import org.example.dip2b.UserRepository;
import org.junit.jupiter.api.Test;

public class DIPTest2 {

    @Test
    void testLegacyUserServiceDependsOnConcreteRepository() throws NoSuchFieldException {
        Field repositoryField = org.example.dip2a.UserService.class.getDeclaredField("repository");

        assertEquals(org.example.dip2a.UserRepositoryImpl.class, repositoryField.getType());
    }

    @Test
    void testLegacyUserServiceStillReturnsUsers() {
        org.example.dip2a.UserService service = new org.example.dip2a.UserService();
        org.example.dip2a.User user = service.getUser(3);

        assertEquals(3, user.getId());
        assertEquals("User 3", user.getName());
    }

    @Test
    void testRefactoredUserServiceDependsOnRepositoryAbstraction()
            throws NoSuchFieldException, NoSuchMethodException {
        Field repositoryField = org.example.dip2b.UserService.class.getDeclaredField("repository");

        assertEquals(UserRepository.class, repositoryField.getType());
        assertNotNull(org.example.dip2b.UserService.class.getDeclaredConstructor(UserRepository.class));
    }

    @Test
    void testRefactoredUserServiceAllowsRepositoryReplacement() {
        UserRepository stubRepository = id -> new org.example.dip2b.User(id, "Stub User");
        org.example.dip2b.UserService service = new org.example.dip2b.UserService(stubRepository);
        org.example.dip2b.User user = service.getUser(7);

        assertEquals(7, user.getId());
        assertEquals("Stub User", user.getName());
    }
}
