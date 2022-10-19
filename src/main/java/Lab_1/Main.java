package Lab_1;

import Lab_1.handler.CancelHandler;
import Lab_1.server.Manager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
//        registerCancelKey();

        System.out.println("-------------------------------------------------------");
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

    private static void registerCancelKey() {
        offConsoleLogger();
        try {
            GlobalScreen.registerNativeHook();
            CancelHandler cancelHandler = new CancelHandler();
            GlobalScreen.addNativeKeyListener(cancelHandler);
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
    }

    private static void offConsoleLogger() {
        String loggerName = GlobalScreen.class.getPackage().getName();
        Logger consoleLogger = Logger.getLogger(loggerName);
        consoleLogger.setLevel(Level.OFF);
        consoleLogger.setUseParentHandlers(false);
    }
}
