package Lab_1;

import Lab_1.server.Manager;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        addCancelListener();

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

    public static void addCancelListener() {
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyPressed(GlobalKeyEvent event) {
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_Q) {
                    Manager.stopComputation();
                }
            }
        });
    }
}
