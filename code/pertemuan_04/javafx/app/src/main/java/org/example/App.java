
/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    public String getGreeting() {
        return "Hello World!";
    }

    @Override
    public void init() {
        System.out.println("Inisialisasi aplikasi");
    }

    @Override
    public void start(Stage primaryStage) {
        
        Label label = new Label("Hello , JavaFX!");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Hello JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Aplikasi dihentikan");
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        launch();
    }
}
