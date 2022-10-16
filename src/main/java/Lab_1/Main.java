package Lab_1;

import Lab_1.server.Manager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please Enter x parameter: ");

        Scanner scanner = new Scanner(System.in);
        String parameter = scanner.next();

        Manager serverManager = new Manager();
        try {
            serverManager.initComputation(parameter);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
