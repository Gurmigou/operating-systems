package Lab_1.client;

import Lab_1.CommunicationCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static Lab_1.CommunicationCommand.RESULT_F;

public abstract class AbstractClient {
    private final Socket serverConnection;
    private final PrintWriter out;
    private final BufferedReader in;
    private final String parameter;
    protected static final String SERVER_ADDRESS = "localhost";
    protected static final int PORT = 8080;
    private static final int MAX_ATTEMPTS = 4;

    public AbstractClient() throws IOException {
        this.serverConnection = new Socket(SERVER_ADDRESS, PORT);
        this.out = new PrintWriter(serverConnection.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));

        // read a parameter from the client handler
        this.parameter = in.readLine();
    }

    public void startClient() {
        try {
            int attempts = 0;
            while (attempts < MAX_ATTEMPTS) {
                int number = (int) (Math.random() * 10);
                if (number < 5) {
                    if (getClientName().equals("F"))
                        out.println(RESULT_F.getMsg() + 5);
                    else
                        out.println(CommunicationCommand.RESULT_G.getMsg() + number);
                } else {
                    out.println(CommunicationCommand.SOFT_ERROR.getMsg());
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                attempts++;
            }
            out.println(CommunicationCommand.HARD_ERROR.getMsg() + "Function " + getClientName() +
                    " failed " + MAX_ATTEMPTS + " times to compute a result.");
        } catch (Exception e) {
            out.println(CommunicationCommand.HARD_ERROR.getMsg() + e.getMessage());
            this.closeConnection();
        }
    }

    public abstract String getClientName();

    protected void closeConnection() {
        try {
            out.close();
            serverConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
