package com.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.User;

public class MultiDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(MultiDatabaseTest.class);

    @Test
    public void testSqliteDatabase() {
        Session session = MultiDatabaseFactory.getSqliteSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User("SQLite Test User");
        Long generatedId = (Long) session.save(user);

        transaction.commit();
        session.close();

        // Retrieve the user from the database using the generated ID
        Session newSession = MultiDatabaseFactory.getSqliteSessionFactory().openSession();
        User savedUser = newSession.get(User.class, generatedId);
        newSession.close();

        logger.info("SQLite - Retrieved User ID: " + savedUser.getId());
        assertNotNull(savedUser.getId(), "User ID should be generated in SQLite");
    }

    @Test
    public void testPostgresqlDatabase() {
        Session session = MultiDatabaseFactory.getPostgresSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User("PostgreSQL Test User");
        Long generatedId = (Long) session.save(user);

        transaction.commit();
        session.close();

        Session newSession = MultiDatabaseFactory.getPostgresSessionFactory().openSession();
        User savedUser = newSession.get(User.class, generatedId);
        newSession.close();

        logger.info("PostgreSQL - Retrieved User ID: " + savedUser.getId());
        assertNotNull(savedUser.getId(), "User ID should be generated in PostgreSQL");
    }

    @Test
    public void testMysqlDatabase() {
        Session session = MultiDatabaseFactory.getMysqlSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User("MySQL Test User");
        Long generatedId = (Long) session.save(user);

        transaction.commit();
        session.close();

        Session newSession = MultiDatabaseFactory.getMysqlSessionFactory().openSession();
        User savedUser = newSession.get(User.class, generatedId);
        newSession.close();

        logger.info("MySQL - Retrieved User ID: " + savedUser.getId());
        assertNotNull(savedUser.getId(), "User ID should be generated in MySQL");
    }
}
