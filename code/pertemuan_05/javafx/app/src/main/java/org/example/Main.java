package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Connection CONNECTION;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            CONNECTION = DriverManager.getConnection("jdbc:mysql://localhost:3306/pradita", "alfa", "1234");
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
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() {
        try {
            if (CONNECTION != null && !CONNECTION.isClosed()) {
                CONNECTION.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
