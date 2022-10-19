package Lab_1.handler;

import Lab_1.server.Manager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import static org.jnativehook.keyboard.NativeKeyEvent.VC_Q;

public class CancelHandler implements NativeKeyListener {
    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        if (event.getKeyCode() == VC_Q) {
            Manager.stopComputation();
        }
    }

    public static void unregisterHook() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}
}
