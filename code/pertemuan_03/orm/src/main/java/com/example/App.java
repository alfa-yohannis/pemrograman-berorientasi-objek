package com.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.model.User;

public class App {


    public static void main(String[] args) {
        Session session = MultiDatabaseFactory.getMysqlSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User("MySQL Test User");
        Long generatedId = (Long) session.save(user);

        transaction.commit();
        session.close();

        Session newSession = MultiDatabaseFactory.getMysqlSessionFactory().openSession();
        User savedUser = newSession.get(User.class, generatedId);
        newSession.close();

        System.out.println("MySQL - Retrieved User ID: " + savedUser.getId());
    }
}
