/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import com.google.common.base.Joiner;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println("Hello, Gradle!");

        // Using function from Google Guava (Maven Central)
        String result = Joiner.on(", ").join("Apple", "Banana", "Cherry");
        System.out.println("Joined String: " + result);

    }
}
