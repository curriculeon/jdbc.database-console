package com.github.perschola;

public class MainApplication {
    public static void main(String[] args) {
        Runnable myObject = new MySqlConnection();
        myObject.run();
    }
}
