package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.example.model.User;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        SessionFactory mysqlFactory = MultiDatabaseFactory.getMysqlSessionFactory();
        SessionFactory postgresFactory = MultiDatabaseFactory.getPostgresSessionFactory();
        SessionFactory sqliteFactory = MultiDatabaseFactory.getSqliteSessionFactory();

        Map<String, Long> generatedIds = new HashMap<>();

        // CREATE ALL USERS FIRST
        generatedIds.put("MySQL", createUser(mysqlFactory, "User MySQL"));
        generatedIds.put("PostgreSQL", createUser(postgresFactory, "User PostgreSQL"));
        generatedIds.put("SQLite", createUser(sqliteFactory, "User SQLite"));

        System.out.println("=== Creation Phase Done ===\n");

        // RETRIEVE ALL USERS
        retrieveUser(mysqlFactory, "MySQL", generatedIds.get("MySQL"));
        retrieveUser(postgresFactory, "PostgreSQL", generatedIds.get("PostgreSQL"));
        retrieveUser(sqliteFactory, "SQLite", generatedIds.get("SQLite"));

        mysqlFactory.close();
        postgresFactory.close();
        sqliteFactory.close();
    }

    private static Long createUser(SessionFactory factory, String name) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        User user = new User(name);

        session.persist(user);  

        tx.commit();            // ID generated here
        session.close();

        return user.getId();    // ID already inside entity
    }

    private static void retrieveUser(SessionFactory factory, String dbName, Long id) {
        Session session = factory.openSession();
        User user = session.get(User.class, id);
        session.close();

        System.out.println(dbName + " -> Retrieved ID: " + user.getId()
                + ", Name: " + user.getName());
    }
}
