package Lab_1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public abstract class AbstractClient {
    protected final Socket serverConnection;
    protected final PrintWriter out;
    protected final BufferedReader in;
    protected String parameter;
    protected static final String SERVER_ADDRESS = "localhost";
    protected static final int PORT = 4005;
    protected static final int MAX_ATTEMPTS = 4;
    protected static final Random random = new Random();

    public AbstractClient() throws IOException {
        this.serverConnection = new Socket(SERVER_ADDRESS, PORT);
        this.out = new PrintWriter(serverConnection.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
    }

    protected void justSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
