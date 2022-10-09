package Lab_1.server;

import Lab_1.CommunicationCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static Lab_1.CommunicationCommand.*;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final String parameter;
    private final String clientName;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientHandler(Socket clientSocket, String parameter, String clientName) throws IOException {
        this.clientSocket = clientSocket;
        this.parameter = parameter;
        this.clientName = clientName;

        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // send a parameter to the client
        this.out.println(parameter);
        this.setDaemon(true);

        this.start();
    }

    @Override
    public void run() {
        try {
            String read = in.readLine();
            System.out.println(read);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try {
//            System.out.println("Starting running");
//            boolean iterate = true;
//            while (iterate) {
//                String response = in.readLine();
//                iterate = handleClientResponse(response);
//            }
//
//            System.out.println("Computation on client: " + clientName + " is finished. Socket closed.");
//
//            closeConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Manager.handleHardError(e.getMessage());
//        }
    }

    private boolean handleClientResponse(String response) {
        if (response == null || response.isEmpty())
            return false;

        if (response.startsWith(RESULT_F.getMsg()) || response.startsWith(RESULT_G.getMsg())) {
            // RESULT_F.length is equal to RESULT_G.length
            double result = Double.parseDouble(response.substring(RESULT_F.getMsg().length()));
            // todo
            System.out.println("Result: " + result);
            return true;
        }
        if (response.startsWith(SOFT_ERROR.getMsg())) {
            System.out.println("Soft error happened on client: " + clientName + ". Trying to recompute...");
            return false;
        }
        if (response.startsWith(HARD_ERROR.getMsg())) {
            Manager.handleHardError("Hard error happened on client: " + clientName + ".");
            return true;
        }

        return false;
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
