package com.example.productmgmt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class Animal {
    String name = "Animal";
    void sound() {
        System.out.println("Generic sound");
    }
}

class Dog extends Animal {
    String name = "Dog";

    void printNames() {
        System.out.println(name);        // Dog
        System.out.println(super.name); // Animal
    }

    void sound() {
        super.sound(); // 呼叫父類別的 sound 方法
        System.out.println("Bark!");
    }
}


public class PassByValueTest {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.sound();
    }
}
