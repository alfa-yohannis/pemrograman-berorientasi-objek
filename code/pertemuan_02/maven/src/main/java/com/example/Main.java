package com.example;

import com.google.common.base.Joiner;

import com.example.Util;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Maven!");

        // Menggunakan fungsi dari library Guava (yang diambil dari Maven Central)
        String result = Joiner.on(", ").join("Apple", "Banana", "Cherry");
        System.out.println("Joined String: " + result);

        Util.sayHello("Ant User");
    }
}