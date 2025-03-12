package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static SessionFactory sessionFactory;
    private static Session session;

    public static void main(String[] args) {
        try {
            // Initialize Hibernate SessionFactory
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            session = sessionFactory.openSession();

            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Main Form");

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        //add styles.css to the scene
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Session getSession() {
        return session;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
